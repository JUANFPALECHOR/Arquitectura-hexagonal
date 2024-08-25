package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.domain.api.usecase.CategoryUseCase;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/*

The controller is a bridge or intermediary that:

Receives HTTP requests (such as requests from a browser or frontend).
Calls business logic (through services) to execute the necessary actions.
Returns an HTTP response with the results, in a way that the client can understand (for example, in JSON format).


 */



@RestController // Indicates that this class is a REST controller
@RequestMapping("/categories") // Defines the base URL for this controller
public class CategoryController {

    private final CategoryUseCase categoryUseCase;

    // builder
    public CategoryController(CategoryUseCase categoryUseCase) {
        this.categoryUseCase = categoryUseCase;
    }

    //@RequestBody is a Spring annotation that indicates that Spring should convert the JSON data sent by the client to an object of type Category.


    @PostMapping// this method will handle HTTP requests of type POST
    public ResponseEntity<String> createCategory(@RequestBody Category category) {
        categoryUseCase.createCategory(category); //Call categoryUseCase Pass the category object received from the client to the createCategory method where the category is validated and saved in the database
        return new ResponseEntity<>("SIUUUUUUUUUUUUUUU", HttpStatus.CREATED);
    }


}
