package com.pragma.usuarios.adapters.driving.http.controller;


import com.pragma.usuarios.adapters.driven.jpa.mysql.mapper.UsuarioMapper;
import com.pragma.usuarios.adapters.driving.http.dto.request.UsuarioRequest;
import com.pragma.usuarios.adapters.driving.http.dto.response.UsuarioResponse;
import com.pragma.usuarios.domain.api.usecase.UsuarioUseCase;
import com.pragma.usuarios.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioController(UsuarioUseCase usuarioUseCase, UsuarioMapper usuarioMapper) {
        this.usuarioUseCase = usuarioUseCase;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UsuarioResponse> crearUsuario(@RequestBody UsuarioRequest usuarioRequest) {
        // Mapear DTO de solicitud a modelo de dominio
        Usuario usuario = usuarioMapper.toDomain(usuarioRequest);

        // Encriptar la contraseña antes de guardarla
        // Asegúrate de implementar la lógica de encriptación en el servicio de aplicación
        usuarioUseCase.execute(usuario);

        // Mapear modelo de dominio a DTO de respuesta
        UsuarioResponse usuarioResponse = usuarioMapper.toResponse(usuario);

        return new ResponseEntity<>(usuarioResponse, HttpStatus.CREATED);
    }

}

