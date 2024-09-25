package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "Auxiliar_bodega")
public class AuxEntity {

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
    private String clave;
    private String rol;

    // Constructores
    public AuxEntity() {}



    private AuxEntity(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.apellido = builder.apellido;
        this.documentoDeIdentidad = builder.documentoDeIdentidad;
        this.celular = builder.celular;
        this.fechaNacimiento = builder.fechaNacimiento;
        this.correo = builder.correo;
        this.clave = builder.clave;
        this.rol = builder.rol;
    }

    // Métodos estáticos para el Builder
    public static class Builder {
        private Long id;
        private String nombre;
        private String apellido;
        private String documentoDeIdentidad;
        private String celular;
        private LocalDate fechaNacimiento;
        private String correo;
        private String clave;
        private String rol;

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

        public Builder withClave(String clave) {
            this.clave = clave;
            return this;
        }

        public Builder withRol(String rol) {
            this.rol = rol;
            return this;
        }

        public AuxEntity build() {
            return new AuxEntity(this);
        }
    }





}
