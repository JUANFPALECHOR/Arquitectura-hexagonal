package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity;

import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "categories")
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = true, length = 90)
    private String description;


}
