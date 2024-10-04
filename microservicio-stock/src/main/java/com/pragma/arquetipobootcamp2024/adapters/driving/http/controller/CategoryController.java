package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.CategoryRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.CategoryResponse;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.CategoryUseCase;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/*

The controller is a bridge or intermediary that:

Receives HTTP requests (such as requests from a browser or frontend).
Calls business logic (through services) to execute the necessary actions.
Returns an HTTP response with the results, in a way that the client can understand (for example, in JSON format).


 */



@RestController // Indicates that this class is a REST controller
@RequestMapping("/api/categories") // Defines the base URL for this controller
public class CategoryController {

    private final CategoryUseCase categoryUseCase;
    private final ICategoryEntityMapper categoryEntityMapper;


    // Constructor
    public CategoryController(CategoryUseCase categoryUseCase, ICategoryEntityMapper categoryEntityMapper) {
        this.categoryUseCase = categoryUseCase;
        this.categoryEntityMapper = categoryEntityMapper;
    }

    //@RequestBody is a Spring annotation that indicates that Spring should convert the JSON data sent by the client to an object of type Category.


    @PostMapping// this method will handle HTTP requests of type POST
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createCategory(@RequestBody CategoryRequest categoryRequest) {
        Category category = categoryEntityMapper.toDomain(categoryRequest);
        categoryUseCase.createCategory(category);
        return new ResponseEntity<>("Category created successfully", HttpStatus.CREATED);
    }


    // ResponseEntity Used to return HTTP responses
    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<CategoryResponse>> listCategories(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection) {
        Page<Category> categories = categoryUseCase.listCategories(page, size, sortDirection);
        Page<CategoryResponse> response = categories.map(categoryEntityMapper::toResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}


