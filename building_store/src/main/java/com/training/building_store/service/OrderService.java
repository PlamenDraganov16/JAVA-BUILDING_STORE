package com.training.building_store.service;

import com.training.building_store.dto.order.OrderRequestDTO;
import com.training.building_store.dto.order.OrderResponseDTO;
import com.training.building_store.dto.orderItem.OrderItemRequestDTO;
import com.training.building_store.exception.ResourceNotFoundException;
import com.training.building_store.mapper.OrderMapper;
import com.training.building_store.model.Order;
import com.training.building_store.model.OrderItem;
import com.training.building_store.model.Product;
import com.training.building_store.model.User;
import com.training.building_store.model.enums.OrderStatus;
import com.training.building_store.repository.OrderItemRepository;
import com.training.building_store.repository.OrderRepository;
import com.training.building_store.repository.ProductRepository;
import com.training.building_store.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    // Create order from request
    public OrderResponseDTO createOrder(Long userId, OrderRequestDTO request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, userId));

        Order order = Order.builder()
                .user(user)
                .status(OrderStatus.NEW)
                .build();

        List<OrderItem> orderItems = request.items().stream()
                .map(itemRequest -> mapToOrderItem(itemRequest, order))
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        BigDecimal total = orderItems.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(total);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.toResponse(savedOrder);
    }

    // Map request item → order item
    private OrderItem mapToOrderItem(OrderItemRequestDTO itemRequest, Order order) {
        Product product = productRepository.findById(itemRequest.productId())
                .orElseThrow(() -> new ResourceNotFoundException(Product.class, itemRequest.productId()));

        return OrderItem.builder()
                .order(order)
                .product(product)
                .quantity(itemRequest.quantity())
                .unitPrice(product.getPrice())
                .subtotal(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity())))
                .build();
    }

    // Get order by ID
    public OrderResponseDTO getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(Order.class, orderId));
        return orderMapper.toResponse(order);
    }

    // Get orders by user
    public List<OrderResponseDTO> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(User.class, userId));
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream().map(orderMapper::toResponse).collect(Collectors.toList());
    }

    // Get orders by status
    public List<OrderResponseDTO> getOrdersByStatus(OrderStatus status) {
        List<Order> orders = orderRepository.findByStatus(status);
        return orders.stream().map(orderMapper::toResponse).collect(Collectors.toList());
    }

    // Update order status
    public OrderResponseDTO updateOrderStatus(Long orderId, OrderStatus newStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(Order.class, orderId));
        order.setStatus(newStatus);
        Order updated = orderRepository.save(order);
        return orderMapper.toResponse(updated);
    }
}
