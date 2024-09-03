package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.domain.spi.ICategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static com.pragma.arquetipobootcamp2024.domain.util.DomainConstants.*;

@Service // indicates that this class is a service
public class CategoryUseCase {

    private final ICategoryRepository categoryRepository;


    public CategoryUseCase(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // This method is responsible for creating a new category.
    public void createCategory(Category category) {
        validateCategory(category);
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            throw new IllegalArgumentException(ERROR_CATEGORY_NAME_EXISTS);
        }
        categoryRepository.save(category);
    }

    // This method verifies that the category being created follows the business rules.
    private void validateCategory(Category category) {
        if (category.getName().length() > 50) {
            throw new IllegalArgumentException(ERROR_CATEGORY_NAME_MAX_LENGTH);
        }
        if (category.getDescription().length() > 90) {
            throw new IllegalArgumentException(ERROR_CATEGORY_DESCRIPTION_MAX_LENGTH);
        }
        if (category.getName().isEmpty() || category.getDescription().isEmpty()) {
            throw new IllegalArgumentException(ERROR_CATEGORY_FIELDS_EMPTY);
        }
    }

    // List categories with pagination and sorting
    public Page<Category> listCategories(int page, int size, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), "name"));
        return categoryRepository.findAll(pageable);
    }

}
