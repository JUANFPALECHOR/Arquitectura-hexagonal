package com.pragma.usuarios.domain.api.usecase;


import com.pragma.usuarios.adapters.driven.jpa.mysql.exception.InvalidCredentialsException;
import com.pragma.usuarios.adapters.driven.jpa.mysql.security.JwtProvider;
import com.pragma.usuarios.domain.model.Usuario;
import com.pragma.usuarios.domain.spi.IUsuarioPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final IUsuarioPersistencePort usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @Autowired
    public AuthService(IUsuarioPersistencePort usuarioRepository, PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public String authenticate(String correo, String rawPassword) {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new InvalidCredentialsException("Correo o contraseña incorrectos."));

        if (!passwordEncoder.matches(rawPassword, usuario.getClaveHash())) {
            throw new InvalidCredentialsException("Correo o contraseña incorrectos.");
        }

        return jwtProvider.generateToken(usuario);
    }
}
