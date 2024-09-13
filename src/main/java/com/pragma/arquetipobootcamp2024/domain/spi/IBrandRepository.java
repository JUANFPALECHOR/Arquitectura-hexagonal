package com.pragma.arquetipobootcamp2024.domain.spi;


import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IBrandRepository {
    void save(Brand brand);
    Optional<Brand> findByName(String name);
    Page<Brand> findAll(Pageable pageable);

}
