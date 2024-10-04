package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.exception.EmailAlreadyExistsException;
import com.pragma.arquetipobootcamp2024.domain.exception.InvalidInputException;
import com.pragma.arquetipobootcamp2024.domain.exception.UnderageUserException;
import com.pragma.arquetipobootcamp2024.domain.model.Rol;
import com.pragma.arquetipobootcamp2024.domain.model.Usuario;
import com.pragma.arquetipobootcamp2024.domain.spi.IUsuarioPersistencePort;
import com.pragma.arquetipobootcamp2024.domain.spi.PasswordEncoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioUseCaseTest {

    private IUsuarioPersistencePort usuarioRepository;
    private PasswordEncoder passwordEncoder;
    private UsuarioUseCase usuarioUseCase;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(IUsuarioPersistencePort.class);
        passwordEncoder = mock(PasswordEncoder.class);
        usuarioUseCase = new UsuarioUseCase(usuarioRepository, passwordEncoder);
    }

    @Test
    void execute_ValidUser_ShouldSaveUserWithDefaultRole() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@example.com");
        usuario.setCelular("+123456789012");
        usuario.setDocumentoDeIdentidad("123456789");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(20));
        usuario.setClaveHash("password123");
        usuario.setRol(null); // Sin rol asignado

        when(usuarioRepository.findByCorreo(anyString())).thenReturn(Optional.empty());
        when(usuarioRepository.findByDocumentoDeIdentidad(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        usuarioUseCase.execute(usuario);

        // Assert
        verify(usuarioRepository, times(1)).save(any(Usuario.class));
        assertEquals("encodedPassword", usuario.getClaveHash());
        assertEquals(Rol.CLIENTE, usuario.getRol()); // Verifica que el rol se asignó correctamente
    }

    @Test
    void execute_InvalidEmail_ShouldThrowException() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setCorreo("invalid-email");
        usuario.setCelular("+123456789012");
        usuario.setDocumentoDeIdentidad("123456789");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(20));
        usuario.setClaveHash("password123");

        // Act & Assert
        InvalidInputException exception = assertThrows(InvalidInputException.class, () -> {
            usuarioUseCase.execute(usuario);
        });

        assertEquals("El correo electrónico es inválido.", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void execute_EmailAlreadyExists_ShouldThrowException() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@example.com");
        usuario.setCelular("+123456789012");
        usuario.setDocumentoDeIdentidad("123456789");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(20));
        usuario.setClaveHash("password123");

        when(usuarioRepository.findByCorreo("test@example.com")).thenReturn(Optional.of(usuario));

        // Act & Assert
        EmailAlreadyExistsException exception = assertThrows(EmailAlreadyExistsException.class, () -> {
            usuarioUseCase.execute(usuario);
        });

        assertEquals("El correo electrónico ya está en uso.", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    void execute_UnderageUser_ShouldThrowException() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setCorreo("test@example.com");
        usuario.setCelular("+123456789012");
        usuario.setDocumentoDeIdentidad("123456789");
        usuario.setFechaNacimiento(LocalDate.now().minusYears(17)); // Menor de edad
        usuario.setClaveHash("password123");

        // Act & Assert
        UnderageUserException exception = assertThrows(UnderageUserException.class, () -> {
            usuarioUseCase.execute(usuario);
        });

        assertEquals("El usuario debe ser mayor de edad.", exception.getMessage());
        verify(usuarioRepository, never()).save(any());
    }

    // Agrega más pruebas para cubrir otros escenarios
}
