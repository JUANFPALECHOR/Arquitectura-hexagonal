package com.pragma.arquetipobootcamp2024.domain.spi;

public interface PasswordEncoder {
    String encode(String rawPassword);
    boolean matches(String rawPassword, String encodedPassword);
}
