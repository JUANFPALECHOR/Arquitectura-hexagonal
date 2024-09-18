package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;


import java.math.BigDecimal;


public class ArticleRequest {

    @NotEmpty(message = "El nombre del artículo es obligatorio")
    private String name;

    private String description;

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser un valor positivo")
    private Integer quantity;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser un valor positivo")
    private BigDecimal price;

    @NotEmpty(message = "Debe haber al menos una categoría asociada")
    private List<String> categories; // Ahora es una lista de nombres de categorías

    // Getters y Setters

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
