package com.training.building_store.dto.category;

import com.training.building_store.model.Category;

public record CategoryResponseDTO(
        Long id,
        String name,
        String description
) {
    public static CategoryResponseDTO fromEntity(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getDescription());
    }
}
