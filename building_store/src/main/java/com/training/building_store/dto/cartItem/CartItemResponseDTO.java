package com.training.building_store.dto.cartItem;

public record CartItemResponseDTO(
        Long id,
        Long productId,
        String productName,
        Double unitPrice,
        Integer quantity,
        Double subtotal
) {}
