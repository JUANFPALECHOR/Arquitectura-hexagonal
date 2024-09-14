package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.api.usecase.CategoryUseCase;
import com.pragma.arquetipobootcamp2024.domain.spi.ICategoryRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.pragma.arquetipobootcamp2024.domain.util.DomainConstants.ERROR_CATEGORY_NAME_EXISTS;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


class CategoryUseCaseTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // We initialize the mocks
    }

    @Test
    void shouldThrowExceptionIfCategoryExists() {
        // Datos de prueba
        Category category = new Category("Electronics", "Description");

        // Simulamos que ya existe una categoría con ese nombre
        when(categoryRepository.findByName("Electronics")).thenReturn(Optional.of(category));

        // Verificamos que se lance la excepción esperada
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryUseCase.createCategory(category);
        });

        assertEquals(ERROR_CATEGORY_NAME_EXISTS, exception.getMessage());
    }

    @Test
    void shouldSaveCategoryIfNew() {
        // Datos de prueba
        Category category = new Category("NewCategory", "Description");

        // Simulamos que no existe una categoría con ese nombre
        when(categoryRepository.findByName("NewCategory")).thenReturn(Optional.empty());

        // Ejecutamos el método que estamos probando
        categoryUseCase.createCategory(category);

        // Verificamos que se haya llamado al método save del repositorio
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testListCategories() {
        // Arrange
        Category category1 = new Category(1L, "Category A", "Description A");
        Category category2 = new Category(2L, "Category Z", "Description Z");
        List<Category> categories = Arrays.asList(category1, category2);

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "name"));
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        // Act
        Page<Category> result = categoryUseCase.listCategories(0, 10, "ASC");

        // Assert
        assertEquals(2, result.getTotalElements());
        assertEquals("Category A", result.getContent().get(0).getName());
        verify(categoryRepository, times(1)).findAll(pageable);
    }

    @Test
    void testListCategoriesDescending() {
        // Arrange
        Category category1 = new Category(1L, "Category A", "Description A");
        Category category2 = new Category(2L, "Category Z", "Description Z");
        List<Category> categories = Arrays.asList(category2, category1); // Z first for DESC order

        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "name"));
        Page<Category> categoryPage = new PageImpl<>(categories, pageable, categories.size());

        when(categoryRepository.findAll(pageable)).thenReturn(categoryPage);

        // Act
        Page<Category> result = categoryUseCase.listCategories(0, 10, "DESC");

        // Assert
        assertEquals(2, result.getTotalElements());
        assertEquals("Category Z", result.getContent().get(0).getName());
        verify(categoryRepository, times(1)).findAll(pageable);
    }


}
