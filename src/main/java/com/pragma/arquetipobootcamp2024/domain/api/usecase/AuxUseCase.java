package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.exception.UnderageAuxException;
import com.pragma.arquetipobootcamp2024.domain.model.Auxiliar_b;
import com.pragma.arquetipobootcamp2024.domain.spi.IAuxRepository;
import com.pragma.arquetipobootcamp2024.domain.spi.PasswordEncoder;

import java.time.LocalDate;
import java.time.Period;

import static com.pragma.arquetipobootcamp2024.domain.util.DomainConstants.ERROR_UNDERAGE_AUX;
import static com.pragma.arquetipobootcamp2024.domain.util.DomainConstants.ROLE_AUX_BODEGA;


public class AuxUseCase {

    private final IAuxRepository auxRepository;
    private final PasswordEncoder passwordEncoder;

    public AuxUseCase(IAuxRepository auxRepository, PasswordEncoder passwordEncoder) {
        this.auxRepository = auxRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(Auxiliar_b aux) {
        // Verifica si el usuario es mayor de edad
        LocalDate today = LocalDate.now();
        int age = Period.between(aux.getFechaNacimiento(), today).getYears();
        if (age < 18) {
            throw new UnderageAuxException(ERROR_UNDERAGE_AUX);
        }

        // Cifra la contraseña
        aux.setClave(passwordEncoder.encode(aux.getClave()));
        // Asigna el rol
        aux.setRol(ROLE_AUX_BODEGA);
        // Guarda el usuario
        auxRepository.save(aux);
    }
}
