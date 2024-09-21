package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.ArticleEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IArticleMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.ArticleRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.ArticleResponse;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.ArticleUseCase;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleUseCase articleUseCase;
    private final IArticleMapper articleMapper;

    public ArticleController(ArticleUseCase articleUseCase, IArticleMapper articleMapper) {
        this.articleUseCase = articleUseCase;
        this.articleMapper = articleMapper;
    }

    @PostMapping
    public ResponseEntity<String> createArticle(@Valid @RequestBody ArticleRequest articleRequest) {
        articleUseCase.createArticle(articleRequest);
        return new ResponseEntity<>("Artículo creado exitosamente.", HttpStatus.CREATED);
    }




}
