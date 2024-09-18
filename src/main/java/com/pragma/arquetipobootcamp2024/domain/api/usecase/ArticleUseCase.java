package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.domain.spi.IArticlePersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ArticleUseCase {

    private final IArticlePersistencePort articlePersistencePort;

    public ArticleUseCase(IArticlePersistencePort articlePersistencePort) {
        this.articlePersistencePort = articlePersistencePort;
    }

    public Article createArticle(Article article) {
        // Log para revisar las categorías antes de la validación
        System.out.println("Categorías del artículo: " + article.getCategories());

        // Validar que el artículo tiene entre 1 y 3 categorías
        if (article.getCategories() == null || article.getCategories().size() < 1 || article.getCategories().size() > 3) {
            throw new IllegalArgumentException("El artículo debe tener entre 1 y 3 categorías");
        }

        // Log después de la validación
        System.out.println("Artículo validado: " + article);

        // Delegar la persistencia del artículo al puerto
        return articlePersistencePort.save(article);
    }


    private boolean hasDuplicateCategories(List<String> categories) {
        return categories.size() != categories.stream().distinct().count();
    }
}