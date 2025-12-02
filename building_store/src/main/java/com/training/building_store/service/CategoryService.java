package com.training.building_store.service;

import com.training.building_store.dto.category.CategoryRequestDTO;
import com.training.building_store.dto.category.CategoryResponseDTO;
import com.training.building_store.exception.ResourceNotFoundException;
import com.training.building_store.mapper.CategoryMapper;
import com.training.building_store.model.Category;
import com.training.building_store.model.Product;
import com.training.building_store.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponseDTO saveCategory(CategoryRequestDTO dto) {
        Category category = categoryMapper.toEntity(dto);
        Category saved =  categoryRepository.save(category);
        return categoryMapper.toDto(saved);
    }

//    public Optional<Category> getCategoryById(Long id) {
//        return categoryRepository.findById(id);
//    }

    public List<CategoryResponseDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteCategoryById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, id));

        // Safely remove products
        Iterator<Product> iterator = category.getProducts().iterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            iterator.remove();        // remove from set
            product.setCategory(null); // detach
        }

        categoryRepository.delete(category);
    }

    @Transactional
    public CategoryResponseDTO pathCategoryName(Long id, String name) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, id));
        category.setName(name);
        return categoryMapper.toDto(category);
    }

    @Transactional
    public CategoryResponseDTO putCategory(Long id, CategoryRequestDTO dto) {
        Category fetched = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, id));
        categoryMapper.updateFromDto(dto, fetched);
        return categoryMapper.toDto(categoryRepository.save(fetched));
    }

    public CategoryResponseDTO getCategoryOrThrow(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Category.class, id));
        return categoryMapper.toDto(category);
    }

    @Transactional
    public Category getCategoryByNameOrCreate(String name) {
        Optional<Category> fetched = categoryRepository.findByName(name);
        return fetched.orElseGet(() -> categoryRepository.save(new Category(name)));
    }
}
