package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.BrandRequest;
import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import com.pragma.arquetipobootcamp2024.domain.spi.IBrandRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.pragma.arquetipobootcamp2024.domain.util.DomainConstants.*;

@Service
public class BrandUseCase {

    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    public BrandUseCase(IBrandRepository brandRepository,IBrandEntityMapper brandEntityMapper) {
        this.brandRepository = brandRepository;
        this.brandEntityMapper = brandEntityMapper;
    }

    public void createBrand(BrandRequest brandRequest) {
        Brand brand = brandEntityMapper.toDomain(brandRequest);

        validateBrand(brand);

        Optional<Brand> existingBrand = brandRepository.findByName(brand.getName());
        if (existingBrand.isPresent()) {
            throw new IllegalArgumentException(ERROR_BRAND_NAME_EXISTS);
        }

        brandRepository.save(brand);
    }


    private void validateBrand(Brand brand) {
        if (brand.getName().length() > 50) {
            throw new IllegalArgumentException(ERROR_BRAND_NAME_MAX_LENGTH);
        }
        if (brand.getDescription().length() > 120) {
            throw new IllegalArgumentException(ERROR_BRAND_DESCRIPTION_MAX_LENGTH);
        }
        if (brand.getName().isEmpty() || brand.getDescription().isEmpty()) {
            throw new IllegalArgumentException(ERROR_BRAND_FIELDS_EMPTY);
        }
    }

    public Page<Brand> listBrands(int page, int size, String sortDirection) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), "name"));
        return brandRepository.findAll(pageable);
    }


}