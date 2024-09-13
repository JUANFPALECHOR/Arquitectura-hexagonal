package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.BrandResponse;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.BrandUseCase;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.BrandRequest;
import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandUseCase brandUseCase;

    public BrandController(BrandUseCase brandUseCase) {
        this.brandUseCase = brandUseCase;
    }

    @PostMapping
    public ResponseEntity<String> createBrand(@RequestBody BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brand.setDescription(brandRequest.getDescription());
        brandUseCase.createBrand(brandRequest);
        return new ResponseEntity<>("Brand created successfully", HttpStatus.CREATED);
    }
    @GetMapping("/brands")
    public ResponseEntity<Page<BrandResponse>> listBrands(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "ASC") String sortDirection) {

        Page<Brand> brands = brandUseCase.listBrands(page, size, sortDirection);
        Page<BrandResponse> brandResponses = brands.map(brandEntityMapper::toResponse);

        return new ResponseEntity<>(brandResponses, HttpStatus.OK);
    }

}