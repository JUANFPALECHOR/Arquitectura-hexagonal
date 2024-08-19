package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.Category;

import java.util.Optional;

public interface ICategoryRepository {
    void save(Category category);
    Optional<Category> findByName(String name);
}
