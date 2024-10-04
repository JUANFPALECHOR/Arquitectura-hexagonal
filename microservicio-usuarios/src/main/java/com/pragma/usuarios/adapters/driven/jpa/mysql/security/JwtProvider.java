package com.pragma.usuarios.adapters.driven.jpa.mysql.security;



import com.pragma.usuarios.domain.model.Usuario;
import io.jsonwebtoken.Claims;
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


    private String jwtSecret = "juan";  // Define directamente el valor para probar

    private long jwtExpirationMs = 86400000;


    // Método para validar el token
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // Método para extraer el correo del token
    public String getCorreoFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // El subject en el token es el correo del usuario
    }

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

