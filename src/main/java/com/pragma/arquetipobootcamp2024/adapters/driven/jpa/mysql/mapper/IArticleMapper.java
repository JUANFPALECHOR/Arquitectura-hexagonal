package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.ArticleRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.ArticleResponse;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IArticleMapper {
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "brand", ignore = true)
    ArticleEntity toEntity(Article article);
    Article toDomain(ArticleEntity articleEntity);
    @Mapping(target = "categories", ignore = true)
    @Mapping(target = "brand", ignore = true)
    Article toDomain(ArticleRequest articleRequest);
    @Mapping(target = "brandId", source = "brand.id")
    ArticleResponse toResponse(Article article);

}


