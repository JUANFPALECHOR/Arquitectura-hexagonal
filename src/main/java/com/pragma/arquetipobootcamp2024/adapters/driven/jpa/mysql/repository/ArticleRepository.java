package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository;



import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository <ArticleEntity, Long> {
}
