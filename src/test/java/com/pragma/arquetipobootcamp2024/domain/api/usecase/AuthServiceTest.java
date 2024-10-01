package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.InvalidCredentialsException;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.security.JwtProvider;
import com.pragma.arquetipobootcamp2024.domain.model.Usuario;
import com.pragma.arquetipobootcamp2024.domain.spi.IUsuarioPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAuthenticateSuccess() {
        // Given
        String correo = "juan@example.com";
        String rawPassword = "password";
        String encodedPassword = "encodedPassword";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setClaveHash(encodedPassword);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);
        when(jwtProvider.generateToken(usuario)).thenReturn("jwtToken");

        // When
        String token = authService.authenticate(correo, rawPassword);

        // Then
        assertEquals("jwtToken", token);
        verify(usuarioRepository).findByCorreo(correo);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verify(jwtProvider).generateToken(usuario);
    }

    @Test
    void testAuthenticateWithInvalidEmail() {
        // Given
        String correo = "incorrect@example.com";
        String rawPassword = "password";

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.empty());

        // When & Then
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            authService.authenticate(correo, rawPassword);
        });

        assertEquals("Correo o contraseña incorrectos.", exception.getMessage());
        verify(usuarioRepository).findByCorreo(correo);
        verifyNoInteractions(passwordEncoder);
        verifyNoInteractions(jwtProvider);
    }

    @Test
    void testAuthenticateWithInvalidPassword() {
        // Given
        String correo = "juan@example.com";
        String rawPassword = "wrongPassword";
        String encodedPassword = "encodedPassword";

        Usuario usuario = new Usuario();
        usuario.setCorreo(correo);
        usuario.setClaveHash(encodedPassword);

        when(usuarioRepository.findByCorreo(correo)).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // When & Then
        InvalidCredentialsException exception = assertThrows(InvalidCredentialsException.class, () -> {
            authService.authenticate(correo, rawPassword);
        });

        assertEquals("Correo o contraseña incorrectos.", exception.getMessage());
        verify(usuarioRepository).findByCorreo(correo);
        verify(passwordEncoder).matches(rawPassword, encodedPassword);
        verifyNoInteractions(jwtProvider);
    }
}
