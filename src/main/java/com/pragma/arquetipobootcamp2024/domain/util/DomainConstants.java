package com.pragma.arquetipobootcamp2024.domain.util;

public final class DomainConstants {
    private DomainConstants() {
        throw new IllegalStateException("Utility class");
    }

    public enum Field {
        NAME,
        PRICE,
        QUANTITY,
        CONTACTNUMBER
    }

    public static final String FIELD_NAME_NULL_MESSAGE = "Field 'name' cannot be null";
    public static final String FIELD_PRICE_NULL_MESSAGE = "Field 'price' cannot be null";
    public static final String FIELD_QUANTITY_NULL_MESSAGE = "Field 'quantity' cannot be null";
    public static final String FIELD_SUPPLIER_NULL_MESSAGE = "Field 'supplier' cannot be null";
    public static final String FIELD_CONTACT_NUMBER_NULL_MESSAGE = "Field 'contact number' cannot be null";

    public static final String ERROR_CATEGORY_NAME_EXISTS = "El nombre de la categoría ya existe.";
    public static final String ERROR_CATEGORY_NAME_MAX_LENGTH = "El tamaño máximo del nombre debe ser de 50 caracteres.";
    public static final String ERROR_CATEGORY_DESCRIPTION_MAX_LENGTH = "El tamaño máximo de la descripción debe ser de 90 caracteres.";
    public static final String ERROR_CATEGORY_FIELDS_EMPTY = "El nombre y la descripción no pueden estar vacíos.";

    public static final String ERROR_BRAND_NAME_EXISTS = "El nombre de la marca ya existe.";
    public static final String ERROR_BRAND_NAME_MAX_LENGTH = "El nombre de la marca debe ser menor o igual a 50 caracteres.";
    public static final String ERROR_BRAND_DESCRIPTION_MAX_LENGTH = "La descripción de la marca debe ser menor o igual a 120 caracteres.";
    public static final String ERROR_BRAND_FIELDS_EMPTY = "El nombre y la descripción de la marca no pueden estar vacíos.";
    public static final String FIELD_CANNOT_BE_EMPTY = "El campo no puede estar vacío.";
    public static final String FIELD_CANNOT_EXCEED_MAX_LENGTH = "El campo no puede exceder el número máximo de caracteres permitido.";

}
