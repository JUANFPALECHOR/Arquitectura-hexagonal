package com.pragma.arquetipobootcamp2024.domain.model;

public class Category {

    private Long id;
    private String name;
    private String description;

    // Constructor sin argumentos
    public Category() {
    }

    // Constructor con todos los parámetros
    public Category(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    // Constructor que acepta solo name y description
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
