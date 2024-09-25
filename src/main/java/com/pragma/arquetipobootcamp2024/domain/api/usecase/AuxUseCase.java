package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.exception.UnderageAuxException;
import com.pragma.arquetipobootcamp2024.domain.model.Aux;
import com.pragma.arquetipobootcamp2024.domain.spi.IAuxRepository;
import com.pragma.arquetipobootcamp2024.domain.spi.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;


public class AuxUseCase {

    private final IAuxRepository auxRepository;
    private final PasswordEncoder passwordEncoder;

    public AuxUseCase(IAuxRepository auxRepository, PasswordEncoder passwordEncoder) {
        this.auxRepository = auxRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(Aux aux) {
        // Verifica si el usuario es mayor de edad
        LocalDate today = LocalDate.now();
        int age = Period.between(aux.getFechaNacimiento(), today).getYears();
        if (age < 18) {
            throw new UnderageAuxException("User must be an adult.");
        }

        // Cifra la contraseña
        aux.setClave(passwordEncoder.encode(aux.getClave()));
        // Asigna el rol
        aux.setRol("aux_bodega");
        // Guarda el usuario
        auxRepository.save(aux);
    }
}
