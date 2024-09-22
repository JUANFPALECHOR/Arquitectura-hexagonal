package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request;

import javax.validation.constraints.*;
import java.util.Set;
import lombok.Data;

@Data
public class ArticleRequest {

    @NotBlank(message = "El nombre del artículo es obligatorio.")
    private String name;

    @NotBlank(message = "La descripción del artículo es obligatoria.")
    private String description;

    @NotNull(message = "La cantidad del artículo es obligatoria.")
    @PositiveOrZero(message = "La cantidad no puede ser negativa.")
    private Integer quantity;

    @NotNull(message = "El precio del artículo es obligatorio.")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor que cero.")
    private Double price;

    @NotNull(message = "Las categorías del artículo son obligatorias.")
    @Size(min = 1, max = 3, message = "El artículo debe tener entre 1 y 3 categorías.")
    private Set<@NotNull(message = "El ID de la categoría no puede ser nulo.") Long> categoryIds;

    @NotNull(message = "El ID de la marca es obligatorio.")
    private Long brandId;

    // Lombok generará getters y setters automáticamente con @Data
}
