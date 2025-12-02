package com.training.building_store.dto.product;

import com.training.building_store.dto.category.CategoryResponseDTO;

import java.math.BigDecimal;
import java.util.Set;

public record ProductResponseDTO(
        Long id,
        String name,
        String description,
        BigDecimal price,
        Double discountPercent,
        BigDecimal discountedPrice,
        Integer stockQuantity,
        String imageUrl,
        CategoryResponseDTO category
) {}
