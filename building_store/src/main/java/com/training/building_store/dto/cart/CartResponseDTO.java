package com.training.building_store.dto.cart;

import com.training.building_store.dto.cartItem.CartItemResponseDTO;

import java.util.List;

public record CartResponseDTO(
        Long id,
        Long userId,
        List<CartItemResponseDTO> items,
        Double totalPrice
) {
}
