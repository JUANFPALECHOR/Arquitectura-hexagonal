package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.UsuarioEntity;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.UsuarioRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.UsuarioResponse;
import com.pragma.arquetipobootcamp2024.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioEntity toEntity(Usuario usuario);
    Usuario toDomain(UsuarioEntity usuarioEntity);
    Usuario toDomain(UsuarioRequest usuarioRequest);
    UsuarioResponse toResponse(Usuario usuario);
}
