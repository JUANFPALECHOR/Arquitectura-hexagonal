package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.domain.spi.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class CategoryUseCase {

    private final ICategoryRepository categoryRepository;

    public CategoryUseCase(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void createCategory(Category category) {
        validateCategory(category);
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("El nombre de la categoría ya existe.");
        }
        categoryRepository.save(category);
    }

    private void validateCategory(Category category) {
        if (category.getName().length() > 50) {
            throw new IllegalArgumentException("El tamaño máximo del nombre debe ser de 50 caracteres.");
        }
        if (category.getDescription().length() > 90) {
            throw new IllegalArgumentException("El tamaño máximo de la descripción debe ser de 90 caracteres.");
        }
        if (category.getName().isEmpty() || category.getDescription().isEmpty()) {
            throw new IllegalArgumentException("El nombre y la descripción no pueden estar vacíos.");
        }
    }
}
