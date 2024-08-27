package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.domain.spi.ICategoryRepository;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryRepository {

    private final CategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public void save(Category category) {
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        log.info("Mapped CategoryEntity: {}", categoryEntity);
        if (categoryEntity.getName() == null) {
            throw new IllegalArgumentException("The name field must not be null");
        }
        categoryRepository.save(categoryEntity);
    }

    @Override
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name)
                .map(categoryEntityMapper::toDomain);
    }

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable)
                .map(categoryEntityMapper::toDomain);  //
    }
}
