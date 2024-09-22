package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IArticleMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.ArticleRepository;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.BrandRepository;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.CategoryRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.domain.spi.IArticlePersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Repository
public class ArticleAdapter implements IArticlePersistencePort {

    private final ArticleRepository articleRepository;
    private final CategoryRepository categoryRepository; // Necesitamos acceder a las categorías
    private final IArticleMapper articleMapper;
    private final BrandRepository brandRepository;

    @Override
    public Article save(Article article) {
        ArticleEntity articleEntity = articleMapper.toEntity(article);

        // Convertir las categorías del dominio a entidades
        Set<CategoryEntity> categoryEntities = new HashSet<>();
        for (Category category : article.getCategories()) {
            CategoryEntity categoryEntity = categoryRepository.findById(category.getId())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con id: " + category.getId()));
            categoryEntities.add(categoryEntity);
        }

        articleEntity.setCategories(categoryEntities);


        //marca
        BrandEntity brandEntity = brandRepository.findById(article.getBrand().getId())
                .orElseThrow(() -> new RuntimeException("Marca no encontrada con id: " + article.getBrand().getId()));
        articleEntity.setBrand(brandEntity);

        ArticleEntity savedEntity = articleRepository.save(articleEntity);
        return articleMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Article> findByName(String name) {
        return articleRepository.findByName(name)
                .map(articleMapper::toDomain);
    }

    @Override
    public Optional<Article> findById(Long id) {
        return articleRepository.findById(id)
                .map(articleMapper::toDomain);
    }


}
