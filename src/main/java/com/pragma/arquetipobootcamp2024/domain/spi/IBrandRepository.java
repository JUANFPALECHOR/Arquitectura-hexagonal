package com.pragma.arquetipobootcamp2024.domain.spi;


import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import java.util.Optional;

public interface IBrandRepository {
    void save(Brand brand);
    Optional<Brand> findByName(String name);

}
