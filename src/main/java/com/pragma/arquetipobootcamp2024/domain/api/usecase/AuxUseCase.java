package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.exception.DocumentAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.domain.exception.EmailAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.domain.exception.InvalidInputException;
import com.pragma.arquetipobootcamp2024.domain.exception.UnderageAuxException;
import com.pragma.arquetipobootcamp2024.domain.model.Auxiliar_b;
import com.pragma.arquetipobootcamp2024.domain.spi.IAuxRepository;
import com.pragma.arquetipobootcamp2024.domain.spi.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

import static com.pragma.arquetipobootcamp2024.domain.util.DomainConstants.*;

@Service
public class AuxUseCase {

    private final IAuxRepository auxRepository;
    private final PasswordEncoder passwordEncoder;

    public AuxUseCase(IAuxRepository auxRepository, PasswordEncoder passwordEncoder) {
        this.auxRepository = auxRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(Auxiliar_b aux) {

        if (!isValidEmail(aux.getCorreo())) {
            throw new InvalidInputException(ERROR_INVALID_EMAIL);
        }

        // Verifica que el teléfono no exceda 13 caracteres y contenga el símbolo +
        if (aux.getCelular().length() > 13 || !aux.getCelular().matches("^\\+\\d{1,12}$")) {
            throw new InvalidInputException(ERROR_INVALID_CELULAR);
        }

        // Verifica que el documento de identidad sea numérico
        if (!aux.getDocumentoDeIdentidad().matches("\\d+")) {
            throw new InvalidInputException(ERROR_INVALID_DOCUMENTO);
        }

        if (auxRepository.findByCorreo(aux.getCorreo()).isPresent()) {
            throw new EmailAlreadyExistsException(ERROR_EMAIL_ALREADY_EXISTS);
        }

        if (auxRepository.findByDocumentoDeIdentidad(aux.getDocumentoDeIdentidad()).isPresent()) {
            throw new DocumentAlreadyExistsException(ERROR_DOCUMENTO_EN_USO);
        }


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
    // Métodos de validación
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }


}
