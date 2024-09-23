package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IArticlePersistencePort {
    Article save(Article article);
    Optional<Article> findByName(String name);
    Optional<Article> findById(Long id);
    Page<Article> findAll(Pageable pageable);
}

