package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.Article;

import java.util.List;
import java.util.Optional;

public interface IArticlePersistencePort {
    Article save(Article article);
    List<Article> getAllArticles();
    Optional<Article> findById(Long id);

}

