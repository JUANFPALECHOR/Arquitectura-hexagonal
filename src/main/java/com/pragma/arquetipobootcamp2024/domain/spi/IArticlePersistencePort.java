package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.pragma.arquetipobootcamp2024.domain.model.Article;

import java.util.Optional;

public interface IArticlePersistencePort {
    Article save(Article article);
    Optional<Article> findByName(String name);
    Optional<Article> findById(Long id);
}

