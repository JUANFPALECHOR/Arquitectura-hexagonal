package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Set;

@AllArgsConstructor
@Data
public class ArticleResponse {

    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private Set<CategorySummary> categories;
    private Long brandId;

    @Data
    public static class CategorySummary {
        private Long id;
        private String name;
    }
}
