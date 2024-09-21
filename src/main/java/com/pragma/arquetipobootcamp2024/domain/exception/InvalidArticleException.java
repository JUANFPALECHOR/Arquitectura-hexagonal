package com.pragma.arquetipobootcamp2024.domain.exception;

/**
 * Excepción lanzada cuando los datos de un artículo son inválidos.
 */
public class InvalidArticleException extends RuntimeException {

    /**
     * Constructor por defecto.
     */
    public InvalidArticleException() {
        super();
    }

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message El mensaje de la excepción.
     */
    public InvalidArticleException(String message) {
        super(message);
    }

    /**
     * Constructor con mensaje y causa.
     *
     * @param message El mensaje de la excepción.
     * @param cause   La causa de la excepción.
     */
    public InvalidArticleException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor con causa.
     *
     * @param cause La causa de la excepción.
     */
    public InvalidArticleException(Throwable cause) {
        super(cause);
    }
}
