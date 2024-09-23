package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response;

import lombok.Data;
import java.util.Set;

@Data
public class ArticleResponse {

    private Long id;
    private String name;
    private Integer quantity;
    private Double price;
    private String brandName;
    private Set<CategorySummary> categories;

    @Data
    public static class CategorySummary {
        private Long id;
        private String name;
    }
}
