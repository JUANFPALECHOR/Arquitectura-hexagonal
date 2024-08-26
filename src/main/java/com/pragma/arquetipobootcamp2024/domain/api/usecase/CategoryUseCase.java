package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.domain.spi.ICategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;



import java.util.Optional;

// It is the class responsible for applying the business rules
// class that is responsible for doing the work that your application needs, such as creating a category, following the necessary rules.

@Service // indicates that this class is a service
public class CategoryUseCase {

    private final ICategoryRepository categoryRepository;

    // builder
    public CategoryUseCase(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    //This method is responsible for creating a new category.
    public void createCategory(Category category) {
        validateCategory(category);
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException("El nombre de la categoría ya existe.");
        }
        categoryRepository.save(category);
    }
    // This method verifies that the category being created follows the business rules.
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

    public Page<Category> listCategories(int page, int size, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), "name"));
        return categoryRepository.findAll(pageable).map(categoryEntityMapper::toDomain);
    }


}
