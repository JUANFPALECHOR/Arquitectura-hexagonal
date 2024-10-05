package com.pragma.usuarios.domain;

import com.pragma.usuarios.domain.api.usecase.UsuarioUseCase;
import com.pragma.usuarios.domain.exception.DocumentAlreadyExistsException;
import com.pragma.usuarios.domain.exception.EmailAlreadyExistsException;
import com.pragma.usuarios.domain.exception.InvalidInputException;
import com.pragma.usuarios.domain.exception.UnderageUserException;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.Usuario;
import com.pragma.usuarios.domain.spi.IUsuarioPersistencePort;
import com.pragma.usuarios.domain.spi.PasswordEncoder;
import com.pragma.usuarios.domain.util.DomainConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Optional;

class UsuarioUseCaseTest {

    @Mock
    private IUsuarioPersistencePort usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UsuarioUseCase usuarioUseCase;

    private Usuario usuarioValido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        usuarioValido = new Usuario();
        usuarioValido.setCorreo("carlos.lopez@example.com");
        usuarioValido.setCelular("+123456789012");
        usuarioValido.setDocumentoDeIdentidad("123456789");
        usuarioValido.setFechaNacimiento(LocalDate.now().minusYears(25));
        usuarioValido.setClaveHash("miContraseña");
        usuarioValido.setRol(Rol.ADMIN);
    }

    @Test
    @DisplayName("Debería crear un usuario exitosamente con datos válidos")
    void execute_UsuarioValido_CreacionExitosa() {
        // Arrange
        when(usuarioRepository.findByCorreo(usuarioValido.getCorreo())).thenReturn(Optional.empty());
        when(usuarioRepository.findByDocumentoDeIdentidad(usuarioValido.getDocumentoDeIdentidad())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(usuarioValido.getClaveHash())).thenReturn("hashedPassword");
        doNothing().when(usuarioRepository).save(usuarioValido);

        // Act
        usuarioUseCase.execute(usuarioValido);

        // Assert
        assertThat(usuarioValido.getClaveHash()).isEqualTo("hashedPassword");
        verify(usuarioRepository, times(1)).findByCorreo(usuarioValido.getCorreo());
        verify(usuarioRepository, times(1)).findByDocumentoDeIdentidad(usuarioValido.getDocumentoDeIdentidad());
        verify(passwordEncoder, times(1)).encode("miContraseña"); // Cambio aquí
        verify(usuarioRepository, times(1)).save(usuarioValido);
    }

    @Test
    @DisplayName("Debería lanzar InvalidInputException para correo inválido")
    void execute_CorreoInvalido_LanzaInvalidInputException() {
        // Arrange
        usuarioValido.setCorreo("correoInvalido"); // Formato de correo incorrecto

        // Act & Assert
        assertThatThrownBy(() -> usuarioUseCase.execute(usuarioValido))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(DomainConstants.ERROR_INVALID_EMAIL);

        verify(usuarioRepository, never()).findByCorreo(anyString());
        verify(usuarioRepository, never()).findByDocumentoDeIdentidad(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar InvalidInputException para celular inválido (más de 13 caracteres)")
    void execute_CelularInvalido_LongitudExcede_LanzaInvalidInputException() {
        // Arrange
        usuarioValido.setCelular("+1234567890123"); // 14 caracteres

        // Act & Assert
        assertThatThrownBy(() -> usuarioUseCase.execute(usuarioValido))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(DomainConstants.ERROR_INVALID_CELULAR);

        verify(usuarioRepository, never()).findByCorreo(anyString());
        verify(usuarioRepository, never()).findByDocumentoDeIdentidad(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar InvalidInputException para celular inválido (formato incorrecto)")
    void execute_CelularInvalido_FormatoIncorrecto_LanzaInvalidInputException() {
        // Arrange
        usuarioValido.setCelular("1234567890"); // No empieza con '+'

        // Act & Assert
        assertThatThrownBy(() -> usuarioUseCase.execute(usuarioValido))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(DomainConstants.ERROR_INVALID_CELULAR);

        verify(usuarioRepository, never()).findByCorreo(anyString());
        verify(usuarioRepository, never()).findByDocumentoDeIdentidad(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar InvalidInputException para documento de identidad inválido (no numérico)")
    void execute_DocumentoInvalido_NoNumerico_LanzaInvalidInputException() {
        // Arrange
        usuarioValido.setDocumentoDeIdentidad("ABC123456"); // Contiene letras

        // Act & Assert
        assertThatThrownBy(() -> usuarioUseCase.execute(usuarioValido))
                .isInstanceOf(InvalidInputException.class)
                .hasMessage(DomainConstants.ERROR_INVALID_DOCUMENTO);

        verify(usuarioRepository, never()).findByCorreo(anyString());
        verify(usuarioRepository, never()).findByDocumentoDeIdentidad(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar EmailAlreadyExistsException si el correo ya existe")
    void execute_EmailYaExiste_LanzaEmailAlreadyExistsException() {
        // Arrange
        when(usuarioRepository.findByCorreo(usuarioValido.getCorreo())).thenReturn(Optional.of(usuarioValido));

        // Act & Assert
        assertThatThrownBy(() -> usuarioUseCase.execute(usuarioValido))
                .isInstanceOf(EmailAlreadyExistsException.class)
                .hasMessage(DomainConstants.ERROR_EMAIL_ALREADY_EXISTS);

        verify(usuarioRepository, times(1)).findByCorreo(usuarioValido.getCorreo());
        verify(usuarioRepository, never()).findByDocumentoDeIdentidad(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar DocumentAlreadyExistsException si el documento ya existe")
    void execute_DocumentoYaExiste_LanzaDocumentAlreadyExistsException() {
        // Arrange
        when(usuarioRepository.findByCorreo(usuarioValido.getCorreo())).thenReturn(Optional.empty());
        when(usuarioRepository.findByDocumentoDeIdentidad(usuarioValido.getDocumentoDeIdentidad()))
                .thenReturn(Optional.of(usuarioValido));

        // Act & Assert
        assertThatThrownBy(() -> usuarioUseCase.execute(usuarioValido))
                .isInstanceOf(DocumentAlreadyExistsException.class)
                .hasMessage(DomainConstants.ERROR_DOCUMENTO_EN_USO);

        verify(usuarioRepository, times(1)).findByCorreo(usuarioValido.getCorreo());
        verify(usuarioRepository, times(1)).findByDocumentoDeIdentidad(usuarioValido.getDocumentoDeIdentidad());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería lanzar UnderageUserException si el usuario es menor de edad")
    void execute_UsuarioMenorDeEdad_LanzaUnderageUserException() {
        // Arrange
        usuarioValido.setFechaNacimiento(LocalDate.now().minusYears(17)); // 17 años

        // Act & Assert
        assertThatThrownBy(() -> usuarioUseCase.execute(usuarioValido))
                .isInstanceOf(UnderageUserException.class)
                .hasMessage(DomainConstants.ERROR_UNDERAGE_USER);

        verify(usuarioRepository, never()).findByCorreo(anyString());
        verify(usuarioRepository, never()).findByDocumentoDeIdentidad(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(usuarioRepository, never()).save(any());
    }

    @Test
    @DisplayName("Debería asignar rol CLIENTE si no está asignado y crear usuario exitosamente")
    void execute_RolNoAsignado_AsignaCliente_CreacionExitosa() {
        // Arrange
        usuarioValido.setRol(null); // Rol no asignado

        when(usuarioRepository.findByCorreo(usuarioValido.getCorreo())).thenReturn(Optional.empty());
        when(usuarioRepository.findByDocumentoDeIdentidad(usuarioValido.getDocumentoDeIdentidad())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(usuarioValido.getClaveHash())).thenReturn("hashedPassword");
        doNothing().when(usuarioRepository).save(usuarioValido);

        // Act
        usuarioUseCase.execute(usuarioValido);

        // Assert
        assertThat(usuarioValido.getRol()).isEqualTo(Rol.CLIENTE);
        assertThat(usuarioValido.getClaveHash()).isEqualTo("hashedPassword");
        verify(usuarioRepository, times(1)).findByCorreo(usuarioValido.getCorreo());
        verify(usuarioRepository, times(1)).findByDocumentoDeIdentidad(usuarioValido.getDocumentoDeIdentidad());
        verify(passwordEncoder, times(1)).encode("miContraseña"); // Cambiado a "miContraseña"
        verify(usuarioRepository, times(1)).save(usuarioValido);
    }
}
