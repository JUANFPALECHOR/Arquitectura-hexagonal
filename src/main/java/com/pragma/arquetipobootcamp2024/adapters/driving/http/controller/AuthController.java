package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.LoginRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.LoginResponse;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Validated @RequestBody LoginRequest loginRequest) {
        String token = authService.authenticate(loginRequest.getCorreo(), loginRequest.getClaveHash());
        return new ResponseEntity<>(new LoginResponse(token, "Inicio de sesión exitoso."), HttpStatus.OK);
    }
}
