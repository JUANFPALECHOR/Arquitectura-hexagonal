package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IArticleMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.ArticleRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.domain.spi.IArticlePersistencePort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ArticleAdapter implements IArticlePersistencePort {

    private final ArticleRepository articleRepository;
    private final IArticleMapper articleMapper;

    public ArticleAdapter(ArticleRepository articleRepository, IArticleMapper articleMapper) {
        this.articleRepository = articleRepository;
        this.articleMapper = articleMapper;
    }

    @Override
    public Article save(Article article) {
        // Convertir Article (dominio) a ArticleEntity (entidad JPA)
        ArticleEntity articleEntity = articleMapper.toEntity(article);
        ArticleEntity savedEntity = articleRepository.save(articleEntity);

        // Convertir de nuevo ArticleEntity a Article para devolverlo
        return articleMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Article> findById(Long id) {
        // Buscar el artículo en la base de datos y mapearlo de entidad a dominio
        Optional<ArticleEntity> optionalEntity = articleRepository.findById(id);
        return optionalEntity.map(articleMapper::toDomain);
    }

    @Override
    public List<Article> findAll() {
        // Obtener todos los artículos y mapearlos de entidad a dominio
        List<ArticleEntity> articleEntities = articleRepository.findAll();
        return articleEntities.stream()
                .map(articleMapper::toDomain)
                .collect(Collectors.toList());
    }
}




