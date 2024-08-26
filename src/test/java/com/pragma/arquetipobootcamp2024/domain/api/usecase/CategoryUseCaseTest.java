package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.api.usecase.CategoryUseCase;
import com.pragma.arquetipobootcamp2024.domain.spi.ICategoryRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;


public class CategoryUseCaseTest {

    @Mock
    private ICategoryRepository categoryRepository; // Simulamos el repositorio

    @InjectMocks
    private CategoryUseCase categoryUseCase; // Este es el objeto que estamos probando

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializamos los mocks
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

        assertEquals("El nombre de la categoría ya existe.", exception.getMessage());
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
}
