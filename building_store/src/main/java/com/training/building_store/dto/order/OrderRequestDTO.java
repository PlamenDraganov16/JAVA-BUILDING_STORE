package com.training.building_store.dto.order;

import com.training.building_store.dto.orderItem.OrderItemRequestDTO;

import java.util.List;

public record OrderRequestDTO(
        List<OrderItemRequestDTO> items
) {
}
