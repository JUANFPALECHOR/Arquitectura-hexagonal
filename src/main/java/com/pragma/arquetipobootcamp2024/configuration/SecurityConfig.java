package com.pragma.arquetipobootcamp2024.configuration;



import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Deshabilitar CSRF para simplificar (no recomendado para producción)
                .csrf().disable()

                // Definir las autorizaciones
                .authorizeRequests()
                .antMatchers("/api/auth/**").permitAll() // Permitir todas las solicitudes a /api/auth/**
                .anyRequest().authenticated() // Requerir autenticación para cualquier otra solicitud
                .and()
                // Configurar el formulario de inicio de sesión (opcional)
                .formLogin().disable(); // Deshabilitar el formulario de inicio de sesión por defecto

        // Puedes agregar filtros personalizados aquí si usas JWT

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Bean para AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}


