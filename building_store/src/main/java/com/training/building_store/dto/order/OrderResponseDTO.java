package com.training.building_store.dto.order;

import com.training.building_store.dto.orderItem.OrderItemResponseDTO;
import com.training.building_store.model.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderResponseDTO(
        Long id,
        Long userId,
        List<OrderItemResponseDTO> items,
        BigDecimal totalPrice,
        OrderStatus status,
        LocalDateTime createdAt
) {
}
