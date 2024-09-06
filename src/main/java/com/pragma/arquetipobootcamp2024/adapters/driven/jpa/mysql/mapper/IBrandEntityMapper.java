package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper;


import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.BrandRequest;
import com.pragma.arquetipobootcamp2024.domain.model.Brand;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.BrandEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {
    BrandEntity toEntity(Brand brand);
    Brand toDomain(BrandEntity brandEntity);
    Brand toDomain(BrandRequest brandRequest);
}
