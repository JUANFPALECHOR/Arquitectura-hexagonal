package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;


@Data
public class AuxRequest {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @NotBlank(message = "El documento de identidad es obligatorio")
    private String documentoDeIdentidad;

    @NotBlank(message = "El celular es obligatorio")
    @Size(max = 13, message = "El celular no puede exceder los 13 caracteres")
    private String celular;

    private LocalDate fechaNacimiento;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo debe ser válido")
    private String correo;

    private String rol;
}
