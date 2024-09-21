package com.pragma.arquetipobootcamp2024.domain.api.usecase;


import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IArticleMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.ArticleRequest;
import com.pragma.arquetipobootcamp2024.domain.exception.ArticleAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.domain.exception.InvalidArticleException;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.domain.spi.IArticlePersistencePort;
import com.pragma.arquetipobootcamp2024.domain.spi.ICategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ArticleUseCase {

    private final IArticlePersistencePort articlePersistencePort;
    private final ICategoryRepository categoryPersistencePort; // Puerto de persistencia para categorías
    private final IArticleMapper articleMapper;

    /**
     * Crea un nuevo artículo en el sistema.
     *
     * @param articleRequest Los datos necesarios para crear el artículo.
     * @return El artículo creado.
     * @throws ArticleAlreadyExistsException Si ya existe un artículo con el mismo nombre.
     * @throws InvalidArticleException       Si los datos proporcionados son inválidos.
     */
    @Transactional
    public Article createArticle(ArticleRequest articleRequest) {
        // Validaciones de negocio
        if (articleRequest.getQuantity() < 0) {
            throw new InvalidArticleException("Quantity cannot be negative.");
        }

        if (articleRequest.getPrice() == null || articleRequest.getPrice() <= 0) {
            throw new InvalidArticleException("Price must be greater than zero.");
        }

        // Validar la cantidad de categorías
        if (articleRequest.getCategoryIds().size() < 1 || articleRequest.getCategoryIds().size() > 3) {
            throw new InvalidArticleException("El artículo debe tener entre 1 y 3 categorías.");
        }

        // Convertir el DTO a dominio
        Article article = articleMapper.toDomain(articleRequest);

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

        // Guardar el artículo
        return articlePersistencePort.save(article);
    }

    // Otros métodos como get, update, delete...
}
