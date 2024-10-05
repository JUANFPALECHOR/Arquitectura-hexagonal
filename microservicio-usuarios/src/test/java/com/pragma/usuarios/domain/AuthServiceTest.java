package com.pragma.usuarios.domain;



import com.pragma.usuarios.adapters.driven.jpa.mysql.exception.InvalidCredentialsException;
import com.pragma.usuarios.adapters.driven.jpa.mysql.security.JwtProvider;
import com.pragma.usuarios.domain.api.usecase.AuthService;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.Usuario;
import com.pragma.usuarios.domain.spi.IUsuarioPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private IUsuarioPersistencePort usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Inicializar los mocks
    }

    @Test
    void authenticate_ValidCredentials_ReturnsToken() {
        // Datos de prueba
        String correo = "admin@correo.com";
        String rawPassword = "password123";
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setClaveHash("hashedPassword");
        usuario.setRol(Rol.ADMIN);

        // Configurar el mock para el repositorio
        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        // Simular que la contraseña en texto plano coincide con la encriptada
        when(passwordEncoder.matches(rawPassword, "hashedPassword")).thenReturn(true);
        // Simular la generación de un JWT
        when(jwtProvider.generateToken(correo, "ADMIN")).thenReturn("test-jwt-token");

        // Llamar al método a probar
        String result = authService.authenticate(correo, rawPassword);

        // Verificar el resultado esperado
        assertEquals("test-jwt-token", result);
        verify(usuarioRepository, times(1)).findByCorreo(correo);
        verify(passwordEncoder, times(1)).matches(rawPassword, "hashedPassword");
        verify(jwtProvider, times(1)).generateToken(correo, "ADMIN");
    }

    @Test
    void authenticate_InvalidEmail_ThrowsException() {
        // Datos de prueba
        String correo = "invalid@correo.com";
        String rawPassword = "password123";

        // Configurar el mock para que el correo no exista en la base de datos
        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.empty());

        // Verificar que se lance una excepción cuando el correo no existe
        assertThrows(InvalidCredentialsException.class, () -> authService.authenticate(correo, rawPassword));

        verify(usuarioRepository, times(1)).findByCorreo(correo);
        verify(passwordEncoder, never()).matches(anyString(), anyString());
        verify(jwtProvider, never()).generateToken(anyString(), anyString());
    }

    @Test
    void authenticate_InvalidPassword_ThrowsException() {
        // Datos de prueba
        String correo = "admin@correo.com";
        String rawPassword = "wrongPassword";
        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setClaveHash("hashedPassword");
        usuario.setRol(Rol.ADMIN);

        // Configurar el mock para que el correo exista
        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        // Simular que la contraseña no coincide
        when(passwordEncoder.matches(rawPassword, "hashedPassword")).thenReturn(false);

        // Verificar que se lance una excepción cuando la contraseña es incorrecta
        assertThrows(InvalidCredentialsException.class, () -> authService.authenticate(correo, rawPassword));

        verify(usuarioRepository, times(1)).findByCorreo(correo);
        verify(passwordEncoder, times(1)).matches(rawPassword, "hashedPassword");
        verify(jwtProvider, never()).generateToken(anyString(), anyString());
    }
}

