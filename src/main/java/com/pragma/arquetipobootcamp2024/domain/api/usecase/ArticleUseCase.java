package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.domain.spi.IArticlePersistencePort;
import java.util.List;

public class ArticleUseCase {

    private final IArticlePersistencePort articlePersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
    }

    public Article createArticle(Article article) {
        // Validar que el artículo tiene entre 1 y 3 categorías
        if (article.getCategories() == null || article.getCategories().isEmpty() || article.getCategories().size() > 3) {
            throw new IllegalArgumentException("El artículo debe tener entre 1 y 3 categorías");
        }

        // Validar que no haya categorías repetidas
        if (hasDuplicateCategories(article.getCategories())) {
            throw new IllegalArgumentException("El artículo no puede tener categorías repetidas");
        }

        // Delegar la persistencia al puerto
        return articlePersistencePort.save(article);
    }

    private boolean hasDuplicateCategories(List<String> categories) {
        return categories.size() != categories.stream().distinct().count();
    }
}