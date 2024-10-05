package com.pragma.usuarios.configuration;



import com.pragma.usuarios.adapters.driven.jpa.mysql.security.JwtAuthenticationFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.pragma.usuarios.configuration.Constants.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()

                //autorizaciones
                .authorizeRequests()
                .antMatchers(API_AUTH).permitAll() // Permitir todas las solicitudes a /api/auth/**
                .antMatchers(API_USUARIOS).hasAuthority(ROLE_ADMIN)


                // Permitir acceso público (sin autenticación) a todos los GET de artículos, marcas y categorías
                .antMatchers(HttpMethod.GET, API_ARTICLES).permitAll()
                .antMatchers(HttpMethod.GET, API_BRANDS).permitAll()
                .antMatchers(HttpMethod.GET, API_CATEGORIES).permitAll()

                // Restringir operaciones POST, PUT y DELETE a usuarios con rol ADMIN
                .antMatchers(HttpMethod.POST, API_ARTICLES).hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.PUT, API_ARTICLES).hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, API_ARTICLES).hasAuthority(ROLE_ADMIN)

                .antMatchers(HttpMethod.POST, API_BRANDS).hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.PUT, API_BRANDS).hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, API_BRANDS).hasAuthority(ROLE_ADMIN)

                .antMatchers(HttpMethod.POST, API_CATEGORIES).hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.PUT, API_CATEGORIES).hasAuthority(ROLE_ADMIN)
                .antMatchers(HttpMethod.DELETE, API_CATEGORIES).hasAuthority(ROLE_ADMIN)



                .anyRequest().authenticated() // Requerir autenticación para cualquier otra solicitud
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class); // Agregar filtro JWT antes de UsernamePasswordAuthenticationFilter


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


