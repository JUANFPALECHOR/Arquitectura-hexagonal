package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;




import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.UsuarioRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.UsuarioResponse;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.UsuarioMapper;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.UsuarioUseCase;
import com.pragma.arquetipobootcamp2024.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioUseCase usuarioUseCase;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioController(UsuarioUseCase usuarioUseCase, UsuarioMapper usuarioMapper) {
        this.usuarioUseCase = usuarioUseCase;
        this.usuarioMapper = usuarioMapper;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> crearUsuario(@RequestBody UsuarioRequest UsuarioRequest) {
        // Mapear DTO de solicitud a modelo de dominio
        Usuario usuario = usuarioMapper.toDomain(UsuarioRequest);

        // Encriptar la contraseña antes de guardarla
        // Asegúrate de implementar la lógica de encriptación en el servicio de aplicación
        usuarioUseCase.execute(usuario);

        // Mapear modelo de dominio a DTO de respuesta
        UsuarioResponse usuarioResponse = usuarioMapper.toResponse(usuario);

        return new ResponseEntity<>(usuarioResponse, HttpStatus.CREATED);
    }

    // Puedes agregar más endpoints según tus necesidades, por ejemplo:
    // @GetMapping("/{correo}")
    // public ResponseEntity<UsuarioResponse> obtenerUsuarioPorCorreo(@PathVariable String correo) { ... }
}

