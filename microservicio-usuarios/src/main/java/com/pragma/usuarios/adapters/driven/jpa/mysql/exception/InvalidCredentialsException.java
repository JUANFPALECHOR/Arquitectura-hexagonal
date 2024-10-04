package com.pragma.usuarios.adapters.driven.jpa.mysql.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}
