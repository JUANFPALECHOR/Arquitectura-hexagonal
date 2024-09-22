package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import com.pragma.arquetipobootcamp2024.domain.model.Category;
import com.pragma.arquetipobootcamp2024.domain.spi.IBrandRepository;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BrandAdapter implements IBrandRepository {

    private final BrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public void save(Brand brand) {
        BrandEntity brandEntity = brandEntityMapper.toEntity(brand);
        brandRepository.save(brandEntity);
    }

    @Override
    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name)
                .map(brandEntityMapper::toDomain);
    }

    @Override
    public Optional<Brand> findById(Long id) {
        return brandRepository.findById(id)
                .map(brandEntityMapper::toDomain);
    }

    @Override
    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable)
                .map(brandEntityMapper::toDomain);  //
    }
}
