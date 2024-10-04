package com.pragma.usuarios.adapters.driven.jpa.mysql.mapper;


import com.pragma.usuarios.adapters.driven.jpa.mysql.entity.UsuarioEntity;
import com.pragma.usuarios.adapters.driving.http.dto.request.UsuarioRequest;
import com.pragma.usuarios.adapters.driving.http.dto.response.UsuarioResponse;
import com.pragma.usuarios.domain.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    UsuarioEntity toEntity(Usuario usuario);
    Usuario toDomain(UsuarioEntity usuarioEntity);
    Usuario toDomain(UsuarioRequest usuarioRequest);
    UsuarioResponse toResponse(Usuario usuario);
}
