package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.domain.spi.ICategoryRepository;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.arquetipobootcamp2024.driven.jpa.mysql.mapper.CategoryMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryRepository {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public void save(Category category) {
        CategoryEntity categoryEntity = categoryMapper.toEntity(category);
        categoryRepository.save(categoryEntity);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryMapper::toDomain);
    }
}
