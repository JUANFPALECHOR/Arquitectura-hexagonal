package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.security;



import com.pragma.arquetipobootcamp2024.domain.model.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * JwtProvider es un componente responsable de la generación y validación de JSON Web Tokens (JWT).
 * Los tokens JWT son utilizados para la autenticación y autorización de usuarios en la aplicación.
 */
@Component
public class JwtProvider {

    // Clave secreta utilizada para firmar los tokens. Debe ser almacenada de manera segura.
    private final String jwtSecret = "juan";
    // Tiempo de expiración del token en milisegundos (1 día en este caso).
    private final long jwtExpirationMs = 86400000; // 1 día en milisegundos

    /**
     * Genera un token JWT para el usuario especificado.
     *
     * @param usuario El objeto Usuario para el cual se generará el token.
     * @return El token JWT generado como una cadena.
     */
    public String generateToken(Usuario usuario) {
        return Jwts.builder()
                .setSubject(usuario.getCorreo()) // Establece el correo del usuario como sujeto del token.
                .claim("id", usuario.getId()) // Agrega el ID del usuario como un reclamo en el token.
                .claim("nombre", usuario.getNombre()) // Agrega el nombre del usuario como un reclamo.
                .claim("apellido", usuario.getApellido()) // Agrega el apellido del usuario como un reclamo.
                .claim("rol", usuario.getRol().name()) // Agrega el rol del usuario como un reclamo.
                .setIssuedAt(new Date()) // Establece la fecha de emisión del token.
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Establece la fecha de expiración.
                .signWith(SignatureAlgorithm.HS512, jwtSecret) // Firma el token con el algoritmo HS512 y la clave secreta.
                .compact(); // Compone el token en una cadena.
    }

}

