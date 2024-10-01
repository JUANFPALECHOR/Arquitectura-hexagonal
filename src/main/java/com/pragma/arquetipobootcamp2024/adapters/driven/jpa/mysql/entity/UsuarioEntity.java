package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity;


import com.pragma.arquetipobootcamp2024.domain.model.Rol;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@ToString(exclude = "claveHash") // Excluye la contraseña del método toString
@Entity
@Table(name = "usuarios") // Cambia el nombre de la tabla si es necesario
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellido;
    private String documentoDeIdentidad;
    private String celular;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    private String correo;
    private String claveHash; // Renombrado para indicar que es una contraseña encriptada

    @Enumerated(EnumType.STRING)
    private Rol rol;

    // Constructor por defecto
    public UsuarioEntity() {}

    // Constructor con Builder (opcional)
    private UsuarioEntity(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.documentoDeIdentidad = builder.documentoDeIdentidad;
        this.celular = builder.celular;
        this.fechaNacimiento = builder.fechaNacimiento;
        this.correo = builder.correo;
        this.claveHash = builder.claveHash;
        this.rol = builder.rol;
    }

    // Métodos estáticos para el Builder (opcional)
    public static class Builder {
        private Long id;
        private String nombre;
        private String apellido;
        private String documentoDeIdentidad;
        private String celular;
        private LocalDate fechaNacimiento;
        private String correo;
        private String claveHash;
        private Rol rol;

        public Builder withId(Long id) {
            this.id = id;
            return this;
        }

        public Builder withNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Builder withApellido(String apellido) {
            this.apellido = apellido;
            return this;
        }

        public Builder withDocumentoDeIdentidad(String documentoDeIdentidad) {
            this.documentoDeIdentidad = documentoDeIdentidad;
            return this;
        }

        public Builder withCelular(String celular) {
            this.celular = celular;
            return this;
        }

        public Builder withFechaNacimiento(LocalDate fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public Builder withCorreo(String correo) {
            this.correo = correo;
            return this;
        }

        public Builder withClaveHash(String claveHash) {
            this.claveHash = claveHash;
            return this;
        }

        public Builder withRol(Rol rol) {
            this.rol = rol;
            return this;
        }

        public UsuarioEntity build() {
            return new UsuarioEntity(this);
        }
    }
}

