package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.ArticleRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.ArticleResponse;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.ArticleUseCase;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<ArticleResponse> createArticle(@RequestBody ArticleRequest articleRequest) {
        Article article = articleMapper.toDomain(articleRequest);
        Article savedArticle = articleUseCase.createArticle(article);
        ArticleResponse response = articleMapper.toResponse(savedArticle);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
