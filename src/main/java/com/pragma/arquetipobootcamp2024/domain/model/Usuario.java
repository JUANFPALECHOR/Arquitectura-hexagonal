package com.pragma.arquetipobootcamp2024.domain.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString(exclude = "claveHash")
public class Usuario {
    private String id;
    private String nombre;
    private String apellido;
    private String documentoDeIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String claveHash; // Contraseña encriptada
    private Rol rol;

}
