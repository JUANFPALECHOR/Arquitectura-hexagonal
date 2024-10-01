package com.pragma.arquetipobootcamp2024.domain.exception;

public class UnderageUserException extends RuntimeException {
    public UnderageUserException(String message) {
        super(message);
    }
}
