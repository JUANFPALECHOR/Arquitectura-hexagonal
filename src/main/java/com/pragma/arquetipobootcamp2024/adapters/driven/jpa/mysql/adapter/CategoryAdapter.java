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

    /**
     * Save a category to the database.
     * First, convert the Category domain object to a JPA entity (CategoryEntity)
     * using the mapper, then saves it to the database.
     *
     * @param category The category of the domain to save.
     * @throws IllegalArgumentException If the category name is null.
     */

    @Override
    public void save(Category category) {
        CategoryEntity categoryEntity = categoryEntityMapper.toEntity(category);
        log.info("Mapped CategoryEntity: {}", categoryEntity);
        if (categoryEntity.getName() == null) {
            throw new IllegalArgumentException("The name field must not be null");
        }

        categoryRepository.save(categoryEntity);// Save the entity to the database
    }


    // Converts the obtained JPA entity to a Category domain object
    @Override
    public Optional<Category> findByName(String name) {
        // Looks up the entity in the database and converts it to a domain object
        return categoryRepository.findByName(name)
                .map(categoryEntityMapper::toDomain);
    }


    // Returns a paginated list of categories.
    @Override
    public Page<Category> findAll(Pageable pageable) {
        // Gets the paged entities and converts them to domain objects
        return categoryRepository.findAll(pageable)
                .map(categoryEntityMapper::toDomain);  //
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryEntityMapper::toDomain);
    }
}
