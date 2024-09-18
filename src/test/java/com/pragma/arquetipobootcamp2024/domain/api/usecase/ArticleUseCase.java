package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.pragma.arquetipobootcamp2024.domain.api.usecase.ArticleUseCase;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.domain.spi.IArticlePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createArticle_validArticle_success() {
        // Arrange
        Article article = new Article();
        article.setName("Smartphone");
        article.setDescription("Latest smartphone");
        article.setPrice(999.99);
        article.setQuantity(10);
        article.setCategories(List.of("Electronics", "Mobile"));

        when(articlePersistencePort.save(article)).thenReturn(article);

        // Act
        Article createdArticle = articleUseCase.createArticle(article);

        // Assert
        assertNotNull(createdArticle);
        assertEquals(2, createdArticle.getCategories().size()); // 2 categorías
        verify(articlePersistencePort, times(1)).save(article); // Verifica que el puerto de persistencia fue llamado 1 vez
    }

    @Test
    void createArticle_noCategories_throwsException() {
        // Arrange
        Article article = new Article();
        article.setName("Smartphone");
        article.setDescription("Latest smartphone");
        article.setPrice(999.99);
        article.setQuantity(10);
        article.setCategories(List.of()); // Sin categorías

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            articleUseCase.createArticle(article);
        });

        assertEquals("El artículo debe tener entre 1 y 3 categorías", thrown.getMessage());
        verify(articlePersistencePort, never()).save(any());

    }

    @Test
    void createArticle_moreThanThreeCategories_throwsException() {
        // Arrange
        Article article = new Article();
        article.setName("Smartphone");
        article.setDescription("Latest smartphone");
        article.setPrice(999.99);
        article.setQuantity(10);
        article.setCategories(List.of("Electronics", "Mobile", "Gadgets", "Technology")); // 4 categorías, más de lo permitido

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            articleUseCase.createArticle(article);
        });

        assertEquals("El artículo debe tener entre 1 y 3 categorías", thrown.getMessage());
        verify(articlePersistencePort, never()).save(any()); // Asegúrate de que no se llamó al puerto de persistencia
    }

    @Test
    void createArticle_duplicateCategories_throwsException() {
        // Arrange
        Article article = new Article();
        article.setName("Smartphone");
        article.setDescription("Latest smartphone");
        article.setPrice(999.99);
        article.setQuantity(10);
        article.setCategories(List.of("Electronics", "Mobile", "Electronics")); // Categorías duplicadas

        // Act & Assert
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> {
            articleUseCase.createArticle(article);
        });

        assertEquals("El artículo no puede tener categorías duplicadas", thrown.getMessage());
        verify(articlePersistencePort, never()).save(any()); // Asegúrate de que no se llamó al puerto de persistencia
    }

    @Test
    void createArticle_persistencePortCalled() {
        // Arrange
        Article article = new Article();
        article.setName("Smartphone");
        article.setDescription("Latest smartphone");
        article.setPrice(999.99);
        article.setQuantity(10);
        article.setCategories(List.of("Electronics", "Mobile"));

        when(articlePersistencePort.save(article)).thenReturn(article);

        // Act
        Article savedArticle = articleUseCase.createArticle(article);

        // Assert
        assertNotNull(savedArticle);
        verify(articlePersistencePort, times(1)).save(article); // Asegúrate de que se llama al puerto una vez
    }



}

