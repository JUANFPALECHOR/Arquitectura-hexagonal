package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper;


import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.AuxEntity;
import com.pragma.arquetipobootcamp2024.domain.model.Auxiliar_b;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IAuxEntityMapper {
    AuxEntity toEntity(Auxiliar_b aux);
    Auxiliar_b toDomain(AuxEntity auxEntity);
}
