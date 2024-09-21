package com.pragma.arquetipobootcamp2024.domain.exception;

/**
 * Excepción lanzada cuando se intenta crear un artículo que ya existe.
 */
public class ArticleAlreadyExistsException extends RuntimeException {

  /**
   * Constructor por defecto.
   */
  public ArticleAlreadyExistsException() {
    super();
  }

  /**
   * Constructor con mensaje personalizado.
   *
   * @param message El mensaje de la excepción.
   */
  public ArticleAlreadyExistsException(String message) {
    super(message);
  }

  /**
   * Constructor con mensaje y causa.
   *
   * @param message El mensaje de la excepción.
   * @param cause   La causa de la excepción.
   */
  public ArticleAlreadyExistsException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor con causa.
   *
   * @param cause La causa de la excepción.
   */
  public ArticleAlreadyExistsException(Throwable cause) {
    super(cause);
  }
}
