package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import com.pragma.arquetipobootcamp2024.domain.spi.IBrandRepository;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
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
}
