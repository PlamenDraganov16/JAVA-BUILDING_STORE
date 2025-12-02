package com.training.building_store.dto.orderItem;

public record OrderItemRequestDTO(
        Long productId,
        Integer quantity
) {
}
