package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.ArticleRequest;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.domain.spi.IArticlePersistencePort;
import com.pragma.arquetipobootcamp2024.domain.spi.IBrandRepository;
import com.pragma.arquetipobootcamp2024.domain.spi.ICategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleUseCaseTest {

    @Mock
    private IArticlePersistencePort articlePersistencePort;

    @Mock
    private ICategoryRepository categoryPersistencePort;

    @Mock
    private IBrandRepository brandPersistencePort;

    @InjectMocks
    private ArticleUseCase articleUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateArticleSuccess() {
        // Arrange
        ArticleRequest request = new ArticleRequest();
        request.setName("Laptop");
        request.setDescription("High-end laptop");
        request.setPrice(1500.99);
        request.setQuantity(10);
        request.setCategoryIds(Set.of(1L));
        request.setBrandId(1L);

        Category category = new Category();
        category.setId(1L);
        category.setName("CategoryName");

        Brand brand = new Brand();
        brand.setId(1L);
        brand.setName("BrandName");

        when(categoryPersistencePort.findById(1L)).thenReturn(Optional.of(category));
        when(brandPersistencePort.findById(1L)).thenReturn(Optional.of(brand));
        when(articlePersistencePort.findByName("Laptop")).thenReturn(Optional.empty());

        Article savedArticle = new Article();
        savedArticle.setId(1L);
        savedArticle.setName("Laptop");
        savedArticle.setDescription("High-end laptop");
        savedArticle.setQuantity(10);
        savedArticle.setPrice(1500.99);
        savedArticle.setCategories(Set.of(category));
        savedArticle.setBrand(brand);

        when(articlePersistencePort.save(any(Article.class))).thenReturn(savedArticle);

        // Act
        Article article = articleUseCase.createArticle(request);

        // Assert
        assertNotNull(article);
        assertEquals(savedArticle.getId(), article.getId());
        assertEquals(savedArticle.getName(), article.getName());
    }
}
