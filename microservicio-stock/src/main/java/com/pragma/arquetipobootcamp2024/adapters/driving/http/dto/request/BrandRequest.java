package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request;


public class BrandRequest {
    private String name;
    private String description;

    // Getters and Setters

    public BrandRequest() {}

    public BrandRequest(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
