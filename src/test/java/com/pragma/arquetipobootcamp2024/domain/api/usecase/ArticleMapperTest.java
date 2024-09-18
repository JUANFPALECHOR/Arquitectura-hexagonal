package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IArticleMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.ArticleRequest;
import com.pragma.arquetipobootcamp2024.domain.model.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ArticleMapperTest {

    @Autowired
    private IArticleMapper articleMapper;

    @Test
    public void testToDomain() {
        ArticleRequest request = new ArticleRequest();
        request.setName("Smartphone");
        request.setDescription("Latest model");
        request.setQuantity(10);
        request.setPrice(new BigDecimal("999.99"));
        request.setCategories(Arrays.asList("Electronics", "Mobile"));  // Ahora se pasan nombres

        Article article = articleMapper.toDomain(request);

        assertNotNull(article);
        assertEquals("Smartphone", article.getName());
        assertEquals("Latest model", article.getDescription());
        assertEquals(10, article.getQuantity());
        assertEquals(new BigDecimal("999.99"), article.getPrice());
        assertTrue(article.getCategories().containsAll(Arrays.asList("Electronics", "Mobile")));
    }
}
