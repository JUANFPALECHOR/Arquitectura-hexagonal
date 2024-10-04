package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;


@Data
@Entity
@Table(name = "brands")
public class BrandEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    // Getters and Setters

    @OneToMany(mappedBy = "brand")
    private Set<ArticleEntity> articles;
}