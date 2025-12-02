package com.training.building_store.dto.cartItem;

public record CartItemRequestDTO(
        Long productId,
        Integer quantity
) {}
