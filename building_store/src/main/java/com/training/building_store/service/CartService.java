package com.training.building_store.service;

import com.training.building_store.dto.cart.CartResponseDTO;
import com.training.building_store.dto.cartItem.CartItemRequestDTO;
import com.training.building_store.exception.ResourceNotFoundException;
import com.training.building_store.mapper.CartMapper;
import com.training.building_store.model.Cart;
import com.training.building_store.model.CartItem;
import com.training.building_store.model.Product;
import com.training.building_store.model.User;
import com.training.building_store.repository.CartItemRepository;
import com.training.building_store.repository.CartRepository;
import com.training.building_store.repository.ProductRepository;
import com.training.building_store.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartMapper cartMapper;

    // Get cart for userId
    public CartResponseDTO getCart(Long userId) {
        User user = getUserById(userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUser(user));
        return cartMapper.toDTO(cart);
    }

    public CartResponseDTO addItem(Long userId, CartItemRequestDTO dto) {
        if (dto == null || dto.quantity() == null || dto.quantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        User user = getUserById(userId);
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUser(user));

        Product product = productRepository.findById(dto.productId())
                .orElseThrow(() -> new ResourceNotFoundException(Product.class, dto.productId()));

        CartItem item = cart.getCartItems().stream()
                .filter(ci -> ci.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newItem = CartItem.builder()
                            .cart(cart)
                            .product(product)
                            .unitPrice(product.getPrice())
                            .quantity(0)
                            .build();
                    cart.getCartItems().add(newItem);
                    return newItem;
                });

        item.setQuantity(item.getQuantity() + dto.quantity());
        item.calculateSubtotal();
        recalcCartTotal(cart);

        cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    public CartResponseDTO updateItem(Long userId, Long cartItemId, Integer quantity) {
        if (quantity == null || quantity <= 0) throw new IllegalArgumentException("Quantity must be positive");

        Cart cart = getCartByUserId(userId);

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException(CartItem.class, cartItemId));

        if (!item.getCart().getId().equals(cart.getId()))
            throw new ResourceNotFoundException(CartItem.class, cartItemId);

        item.setQuantity(quantity);
        item.calculateSubtotal();
        recalcCartTotal(cart);
        cartRepository.save(cart);

        return cartMapper.toDTO(cart);
    }

    public CartResponseDTO removeItem(Long userId, Long cartItemId) {
        Cart cart = getCartByUserId(userId);

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new ResourceNotFoundException(CartItem.class, cartItemId));

        if (!item.getCart().getId().equals(cart.getId()))
            throw new ResourceNotFoundException(CartItem.class, cartItemId);

        cart.getCartItems().remove(item);
        cartItemRepository.delete(item);
        recalcCartTotal(cart);
        cartRepository.save(cart);

        return cartMapper.toDTO(cart);
    }

    public CartResponseDTO clearCart(Long userId) {
        Cart cart = getCartByUserId(userId);
        cart.getCartItems().clear();
        recalcCartTotal(cart);
        cartRepository.save(cart);
        return cartMapper.toDTO(cart);
    }

    // Helpers
    private User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, userId));
    }

    private Cart getCartByUserId(Long userId) {
        User user = getUserById(userId);
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> createCartForUser(user));
    }

    private Cart createCartForUser(User user) {
        Cart cart = Cart.builder()
                .user(user)
                .totalPrice(BigDecimal.ZERO)
                .build();
        Cart saved = cartRepository.save(cart);
        user.setCart(saved);
        userRepository.save(user);
        return saved;
    }

    private void recalcCartTotal(Cart cart) {
        BigDecimal total = cart.getCartItems().stream()
                .map(ci -> ci.getSubtotal() == null ? BigDecimal.ZERO : ci.getSubtotal())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalPrice(total);
    }
}
