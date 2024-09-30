package com.pragma.arquetipobootcamp2024.configuration;



import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/auxiliar").permitAll() // Permitir acceso a la ruta auxiliar
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
                .and()
                .httpBasic()
                .and()
                .csrf().disable();

        return http.build();
    }
}


