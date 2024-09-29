package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.AuxRequest;
import com.pragma.arquetipobootcamp2024.domain.api.usecase.AuxUseCase;
import com.pragma.arquetipobootcamp2024.domain.model.Auxiliar_b;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class AuxController {

    private final AuxUseCase auxUseCase;
    private final

    public AuxController(AuxUseCase auxUseCase) {
        this.auxUseCase = auxUseCase;
    }



    @PostMapping
    public ResponseEntity<Void> createAuxBodega(@Valid @RequestBody AuxRequest auxRequest) {
        // Usar el mapper para convertir el DTO a modelo de dominio
        Auxiliar_b aux = auxMapper.toDomain(auxRequest);

        // Ejecutar el caso de uso
        auxUseCase.execute(aux);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
















}
