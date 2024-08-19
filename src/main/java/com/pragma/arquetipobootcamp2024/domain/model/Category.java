package com.pragma.arquetipobootcamp2024.domain.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Category {

    private Long id;
    private String name;
    private String description;


    }

