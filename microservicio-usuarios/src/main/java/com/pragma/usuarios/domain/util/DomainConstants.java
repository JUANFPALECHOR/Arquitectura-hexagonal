package com.pragma.usuarios.domain.util;

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



    public static final String ERROR_EMAIL_ALREADY_EXISTS = "El correo electrónico ya está en uso.";
    public static final String ERROR_DOCUMENTO_EN_USO = "El documento de identidad ya está en uso.";

    public static final String ERROR_INVALID_EMAIL = "El correo electrónico es inválido.";
    public static final String ERROR_INVALID_CELULAR = "El celular debe contener un máximo de 13 caracteres y puede contener el símbolo +.";
    public static final String ERROR_INVALID_DOCUMENTO = "El documento de identidad debe ser numérico.";

    public static final String ERROR_UNDERAGE_USER = "El usuario debe ser mayor de edad.";
    public static final String ROLE_USER = "ROLE_USER";



}
