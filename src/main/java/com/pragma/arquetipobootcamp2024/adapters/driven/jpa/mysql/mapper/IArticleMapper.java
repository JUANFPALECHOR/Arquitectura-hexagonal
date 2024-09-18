package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.ArticleRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.ArticleResponse;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IArticleMapper {

    ArticleEntity toEntity(Article article);

    Article toDomain(ArticleEntity articleEntity);

    Article toDomain(ArticleRequest articleRequest);

    ArticleResponse toResponse(Article article);

    default List<String> mapCategoriesToStrings(Set<CategoryEntity> categories) {
        List<String> categoryNames = categories.stream()
                .map(CategoryEntity::getName)
                .collect(Collectors.toList());

        // Log para verificar el mapeo
        System.out.println("Mapeo de categorías a strings: " + categoryNames);

        return categoryNames;
    }

    default Set<CategoryEntity> mapStringsToCategories(List<String> categoryNames) {
        Set<CategoryEntity> categories = categoryNames.stream()
                .map(name -> {
                    CategoryEntity category = new CategoryEntity();
                    category.setName(name);
                    return category;
                })
                .collect(Collectors.toSet());

        // Log para verificar el mapeo de strings a categorías
        System.out.println("Mapeo de strings a categorías: " + categories);

        return categories;
    }
}


