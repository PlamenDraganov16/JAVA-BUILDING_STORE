package com.training.building_store.mapper;

import com.training.building_store.dto.order.OrderResponseDTO;
import com.training.building_store.dto.orderItem.OrderItemResponseDTO;
import com.training.building_store.model.Order;
import com.training.building_store.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "items")
    OrderResponseDTO toResponse(Order order);

    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "product.name", target = "productName")
    OrderItemResponseDTO toItemResponse(OrderItem orderItem);

    List<OrderItemResponseDTO> toItemResponseList(List<OrderItem> items);
}
