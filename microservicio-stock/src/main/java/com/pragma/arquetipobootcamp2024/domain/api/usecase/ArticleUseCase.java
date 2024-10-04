package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.ArticleRequest;
import com.pragma.arquetipobootcamp2024.domain.exception.ArticleAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.domain.exception.InvalidArticleException;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.domain.spi.IArticlePersistencePort;
import com.pragma.arquetipobootcamp2024.domain.spi.IBrandRepository;
import com.pragma.arquetipobootcamp2024.domain.spi.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticleUseCase {

    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryRepository categoryPersistencePort;
    private final IBrandRepository brandPersistencePort;

    @Transactional
    public Article createArticle(ArticleRequest articleRequest) {
        // Validaciones de negocio
        if (articleRequest.getQuantity() < 0) {
            throw new InvalidArticleException("Quantity cannot be negative.");
        }

        if (articleRequest.getPrice() == null || articleRequest.getPrice() <= 0) {
            throw new InvalidArticleException("Price must be greater than zero.");
        }

        if (articleRequest.getCategoryIds().isEmpty() || articleRequest.getCategoryIds().size() > 3) {
            throw new InvalidArticleException("El artículo debe tener entre 1 y 3 categorías.");
        }

        // Crear el objeto Article manualmente
        Article article = new Article();
        article.setName(articleRequest.getName());
        article.setDescription(articleRequest.getDescription());
        article.setPrice(articleRequest.getPrice());
        article.setQuantity(articleRequest.getQuantity());

        // Manejar las categorías
        Set<Category> categories = new HashSet<>();
        for (Long categoryId : articleRequest.getCategoryIds()) {
            Category category = categoryPersistencePort.findById(categoryId)
                    .orElseThrow(() -> new InvalidArticleException("Categoría no encontrada con id: " + categoryId));
            categories.add(category);
        }

        article.setCategories(categories);

        // Verificar si ya existe un artículo con el mismo nombre
        Optional<Article> existingArticle = articlePersistencePort.findByName(article.getName());
        if (existingArticle.isPresent()) {
            throw new ArticleAlreadyExistsException("Article with name " + article.getName() + " already exists.");
        }

        // Manejar la marca
        if (articleRequest.getBrandId() != null) {
            Brand brand = brandPersistencePort.findById(articleRequest.getBrandId())
                    .orElseThrow(() -> new InvalidArticleException("Marca no encontrada con id: " + articleRequest.getBrandId()));
            article.setBrand(brand);
        } else {
            article.setBrand(null); // O simplemente no asignes nada
        }

        // Guardar el artículo
        return articlePersistencePort.save(article);


    }

    public Page<Article> listArticles(int page, int size, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), "name"));
        return articlePersistencePort.findAll(pageable);
    }
}
