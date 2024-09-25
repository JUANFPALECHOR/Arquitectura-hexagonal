package com.pragma.arquetipobootcamp2024.domain.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.Period;


@Data
public class Aux {
    private String nombre;
    private String apellido;
    private String documentoDeIdentidad;
    private String celular;
    private LocalDate fechaNacimiento;
    private String correo;
    private String clave;
    private String rol;



}
