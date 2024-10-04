package com.pragma.usuarios.domain.api.usecase;

import com.pragma.usuarios.domain.exception.DocumentAlreadyExistsException;
import com.pragma.usuarios.domain.exception.EmailAlreadyExistsException;
import com.pragma.usuarios.domain.exception.InvalidInputException;
import com.pragma.usuarios.domain.exception.UnderageUserException;
import com.pragma.usuarios.domain.model.Rol;
import com.pragma.usuarios.domain.model.Usuario;
import com.pragma.usuarios.domain.spi.IUsuarioPersistencePort;
import com.pragma.usuarios.domain.spi.PasswordEncoder;
import com.pragma.usuarios.domain.util.DomainConstants;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;


@Service
public class UsuarioUseCase {

    private final IUsuarioPersistencePort usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioUseCase(IUsuarioPersistencePort usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void execute(Usuario usuario) {

        // Validación de correo electrónico
        if (!isValidEmail(usuario.getCorreo())) {
            throw new InvalidInputException(DomainConstants.ERROR_INVALID_EMAIL);
        }

        // Validación del número de celular
        if (usuario.getCelular().length() > 13 || !usuario.getCelular().matches("^\\+\\d{1,12}$")) {
            throw new InvalidInputException(DomainConstants.ERROR_INVALID_CELULAR);
        }

        // Validación del documento de identidad
        if (!usuario.getDocumentoDeIdentidad().matches("\\d+")) {
            throw new InvalidInputException(DomainConstants.ERROR_INVALID_DOCUMENTO);
        }

        // Verificar si el correo electrónico ya existe
        if (usuarioRepository.findByCorreo(usuario.getCorreo()).isPresent()) {
            throw new EmailAlreadyExistsException(DomainConstants.ERROR_EMAIL_ALREADY_EXISTS);
        }

        // Verificar si el documento de identidad ya existe
        if (usuarioRepository.findByDocumentoDeIdentidad(usuario.getDocumentoDeIdentidad()).isPresent()) {
            throw new DocumentAlreadyExistsException(DomainConstants.ERROR_DOCUMENTO_EN_USO);
        }

        // Verificar si el usuario es mayor de edad
        LocalDate today = LocalDate.now();
        int age = Period.between(usuario.getFechaNacimiento(), today).getYears();
        if (age < 18) {
            throw new UnderageUserException(DomainConstants.ERROR_UNDERAGE_USER);
        }

        // Cifrar la contraseña
        usuario.setClaveHash(passwordEncoder.encode(usuario.getClaveHash()));

        // Asignar el rol predeterminado si no está asignado
        if (usuario.getRol() == null) {
            usuario.setRol(Rol.CLIENTE);
        }

        // Guardar el usuario
        usuarioRepository.save(usuario);
    }

    // Método de validación de correo electrónico
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email != null && email.matches(emailRegex);
    }
}
