package com.pragma.arquetipobootcamp2024.domain.exception;

public class DocumentAlreadyExistsException extends RuntimeException {
    public DocumentAlreadyExistsException(String message) {

        super(message);
    }
}
