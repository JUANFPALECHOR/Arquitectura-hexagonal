package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


//JPA (a framework for interacting with databases)

//makes the connection between your domain and the database.

//This interface inherits from JpaRepository, which gives me out-of-the-box methods
// like save(), findById(), deleteById(), etc., to use in CategoryEntity

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);//search a category by name
    Page<CategoryEntity> findAll(Pageable pageable);
}


