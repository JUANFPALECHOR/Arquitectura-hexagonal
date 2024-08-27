package com.pragma.arquetipobootcamp2024.domain.spi;

import com.pragma.arquetipobootcamp2024.domain.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;



//Class in which I tell him that he must maintain his category but without telling him how.



public interface ICategoryRepository {
    void save(Category category); // Save a Category but returns nothing
    Optional<Category> findByName(String name);
    Page<Category> findAll(Pageable pageable);//search a category by name
}
