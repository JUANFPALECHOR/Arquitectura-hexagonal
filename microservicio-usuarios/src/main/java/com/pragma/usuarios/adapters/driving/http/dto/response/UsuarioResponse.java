package com.pragma.usuarios.adapters.driving.http.dto.response;

import com.pragma.usuarios.domain.model.Rol;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UsuarioResponse {
    private String id;
    private String nombre;
    private String apellido;
    private String documentoDeIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private Rol rol;
}