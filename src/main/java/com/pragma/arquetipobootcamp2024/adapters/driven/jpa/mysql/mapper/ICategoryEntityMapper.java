package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.CategoryRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.CategoryResponse;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.CategoryEntity;

import org.mapstruct.Mapper;

/*
example of how a mapper works

Imagine that you work in an international company, and your team has people who speak different languages.
You work in the Paris office and speak French (representing the domain), but your business partner in New York only speaks English (representing the database).

The mapper would be like a translator. The translator converts your message from French to English when you talk to New York,
and then converts the message back to French when you receive a reply. Thus, both can understand each other without learning each other's language.

Simply put, the mapper makes sure that both the domain (which contains the business logic) and the database (which stores the data) can "speak the same language."
 */


// The generated mapper should be managed by Spring as a bean.
// This is used to inject the mapper into different classes
@Mapper(componentModel = "spring")
public interface ICategoryEntityMapper {
    CategoryEntity toEntity(Category category);
    Category toDomain(CategoryEntity categoryEntity);
    Category toDomain(CategoryRequest categoryRequest);
    CategoryResponse toResponse(Category category);
}
