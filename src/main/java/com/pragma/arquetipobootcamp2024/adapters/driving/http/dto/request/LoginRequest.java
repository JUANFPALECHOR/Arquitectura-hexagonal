package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {

    @NotBlank(message = "El correo electrónico es obligatorio.")
    @Email(message = "El correo electrónico es inválido.")
    private String correo;

    @NotBlank(message = "La contraseña es obligatoria.")
    private String claveHash;
}
