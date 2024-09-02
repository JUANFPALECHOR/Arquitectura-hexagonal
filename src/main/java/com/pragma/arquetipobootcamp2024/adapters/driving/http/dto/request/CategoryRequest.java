package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request;

public class CategoryRequest {
    private String name;
    private String description;

    // Constructor, getters y setters
    public CategoryRequest() {}

    public CategoryRequest(String name, String description) {
        this.name = name;
        this.description = description;
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
}
