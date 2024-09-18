package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.ArticleRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.domain.spi.IArticlePersistencePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ArticleAdapter implements IArticlePersistencePort {

    private final ArticleRepository articleRepository;

    public ArticleAdapter(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @Override
    public Article save(Article article) {
        return articleRepository.save(article); // Aquí estamos usando JPA para guardar
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id); // Usando JPA para obtener un artículo por ID
    }

    @Override
    public List<Article> findAll() {
        return articleRepository.findAll(); // Usando JPA para obtener todos los artículos
    }
}



