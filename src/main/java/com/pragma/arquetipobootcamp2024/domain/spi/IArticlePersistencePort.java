package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.Article;

import java.util.List;
import java.util.Optional;

public interface IArticlePersistencePort {
    // Método para guardar un artículo
    Article save(Article article);

    // Método para obtener un artículo por ID
    Optional<Article> findById(Long id);

    // Método para obtener todos los artículos
    List<Article> findAll();
}
