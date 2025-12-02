package com.training.building_store.mapper;

import com.training.building_store.dto.cart.CartResponseDTO;
import com.training.building_store.dto.cartItem.CartItemResponseDTO;
import com.training.building_store.model.Cart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CartItemMapper.class})
public interface CartMapper extends CartItemMapper {

    @Mapping(source = "user.id", target = "userId")
    default CartResponseDTO toDTO(Cart cart) {
        if (cart == null) return null;

        List<CartItemResponseDTO> items = cart.getCartItems()
                .stream()
                .map(this::toDTO)  // uses CartItemMapper
                .toList();

        return new CartResponseDTO(
                cart.getId(),
                cart.getUser().getId(),
                items,
                cart.getTotalPrice().doubleValue()
        );
    }
}

