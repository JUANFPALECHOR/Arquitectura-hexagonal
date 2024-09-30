package com.pragma.arquetipobootcamp2024.domain.api.usecase;



import com.pragma.arquetipobootcamp2024.domain.exception.EmailAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.domain.exception.InvalidInputException;
import com.pragma.arquetipobootcamp2024.domain.exception.UnderageAuxException;
import com.pragma.arquetipobootcamp2024.domain.model.Auxiliar_b;
import com.pragma.arquetipobootcamp2024.domain.spi.IAuxRepository;
import com.pragma.arquetipobootcamp2024.domain.spi.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class AuxUseCaseTest {

    private IAuxRepository auxRepository;
    private PasswordEncoder passwordEncoder;
    private AuxUseCase auxUseCase;

    @BeforeEach
    void setUp() {
        auxRepository = mock(IAuxRepository.class);
        passwordEncoder = mock(PasswordEncoder.class);
        auxUseCase = new AuxUseCase(auxRepository, passwordEncoder);
    }

    @Test
    void testCreateAuxSuccessfully() {
        // Given
        Auxiliar_b aux = new Auxiliar_b();
        aux.setNombre("Juan");
        aux.setApellido("Pérez");
        aux.setCorreo("juan@example.com");
        aux.setDocumentoDeIdentidad("123456789");
        aux.setCelular("+573005698325");
        aux.setFechaNacimiento(LocalDate.now().minusYears(17));
        aux.setClave("password");



        when(auxRepository.findByCorreo(aux.getCorreo())).thenReturn(Optional.empty());
        when(auxRepository.findByDocumentoDeIdentidad(aux.getDocumentoDeIdentidad())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(aux.getClave())).thenReturn("encodedPassword");

        // When & Then
        assertThrows(EmailAlreadyExistsException.class, () -> {
            auxUseCase.execute(aux);
        });
    }

    @Test
    void testCreateAuxWithEmailAlreadyExists() {
        // Given
        Auxiliar_b aux = new Auxiliar_b();
        aux.setNombre("Juan");
        aux.setApellido("Pérez");
        aux.setCorreo("juan@example.com");
        aux.setDocumentoDeIdentidad("123456789");
        aux.setCelular("+573005698325");
        aux.setFechaNacimiento(LocalDate.now().minusYears(18));  // Mayor de edad
        aux.setClave("password");

        // Simulamos que el correo ya existe en el repositorio
        when(auxRepository.findByCorreo(aux.getCorreo())).thenReturn(Optional.of(aux));
        when(auxRepository.findByDocumentoDeIdentidad(aux.getDocumentoDeIdentidad())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EmailAlreadyExistsException.class, () -> {
            auxUseCase.execute(aux);
        });
    }

    @Test
    void testCreateAuxUnderage() {
        // Given
        Auxiliar_b aux = new Auxiliar_b();
        aux.setNombre("Juan");
        aux.setApellido("Pérez");
        aux.setCorreo("juan@example.com");
        aux.setDocumentoDeIdentidad("123456789");
        aux.setCelular("+573005698325");
        aux.setFechaNacimiento(LocalDate.now().minusYears(17));  // Menor de edad
        aux.setClave("password");

        // Simulamos que el correo y el documento no existen en el repositorio
        when(auxRepository.findByCorreo(aux.getCorreo())).thenReturn(Optional.empty());
        when(auxRepository.findByDocumentoDeIdentidad(aux.getDocumentoDeIdentidad())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(aux.getClave())).thenReturn("encodedPassword");

        // When & Then
        assertThrows(UnderageAuxException.class, () -> {
            auxUseCase.execute(aux);
        });
    }


    @Test
    void testCreateAuxWithInvalidEmail() {
        // Given
        Auxiliar_b aux = new Auxiliar_b();
        aux.setCorreo("invalid-email");

        // When & Then
        assertThrows(InvalidInputException.class, () -> {
            auxUseCase.execute(aux);
        });
    }

    @Test
    void testCreateAuxWithInvalidPhone() {
        // Given
        Auxiliar_b aux = new Auxiliar_b();
        aux.setCelular("12345678901234"); // Excede 13 caracteres

        // When & Then
        assertThrows(InvalidInputException.class, () -> {
            auxUseCase.execute(aux);
        });
    }

    @Test
    void testCreateAuxWithNonNumericDocument() {
        // Given
        Auxiliar_b aux = new Auxiliar_b();
        aux.setDocumentoDeIdentidad("ABC123"); // No numérico

        // When & Then
        assertThrows(InvalidInputException.class, () -> {
            auxUseCase.execute(aux);
        });
    }





}

