package com.training.building_store.mapper;

import com.training.building_store.dto.category.CategoryRequestDTO;
import com.training.building_store.dto.category.CategoryResponseDTO;
import com.training.building_store.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface CategoryMapper {
    CategoryResponseDTO toDto(Category category);

    Category toEntity(CategoryRequestDTO dto);

    void updateFromDto(CategoryRequestDTO dto, @MappingTarget Category category);
}
