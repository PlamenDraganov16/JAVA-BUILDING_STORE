package com.training.building_store.mapper;

import com.training.building_store.dto.cartItem.CartItemResponseDTO;
import com.training.building_store.model.CartItem;

public interface CartItemMapper {

    default CartItemResponseDTO toDTO(CartItem cartItem) {
        if (cartItem == null) {
            return null;
        }

        return new CartItemResponseDTO(
                cartItem.getId(),
                cartItem.getProduct().getId(),
                cartItem.getProduct().getName(),
                cartItem.getUnitPrice().doubleValue(),
                cartItem.getQuantity(),
                cartItem.getSubtotal().doubleValue()
        );
    }
}
