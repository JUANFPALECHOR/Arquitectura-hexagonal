package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request;

import com.pragma.arquetipobootcamp2024.domain.model.Rol;
import lombok.Data;


import java.time.LocalDate;


@Data
public class UsuarioRequest {
    private String nombre;
    private String apellido;
    private String documentoDeIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String claveHash; // Contraseña en texto plano, que luego encriptarás
    private Rol rol;
}
