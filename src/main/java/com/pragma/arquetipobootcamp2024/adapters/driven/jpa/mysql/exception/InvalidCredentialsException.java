package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
