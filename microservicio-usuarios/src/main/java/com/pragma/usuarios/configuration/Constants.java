package com.pragma.usuarios.configuration;

public class Constants {

    public static final String API_AUTH = "/api/auth/**";
    public static final String API_USUARIOS = "/api/usuarios/**";
    public static final String API_ARTICLES = "/api/articles/**";
    public static final String API_BRANDS = "/api/brands/**";
    public static final String API_CATEGORIES = "/api/categories/**";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";

    // Constructor privado para evitar instanciación
    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}
