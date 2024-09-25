package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.AuxEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.AuxRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Auxiliar_b;

public class AuxAdapter {

    private final AuxRepository auxRepository;

    public AuxAdapter(AuxRepository auxRepository) {
        this.auxRepository = auxRepository;

    }
    public static AuxEntity toEntity(Auxiliar_b aux) {
        AuxEntity entity = new AuxEntity();
        entity.setNombre(aux.getNombre());
        entity.setApellido(aux.getApellido());
        entity.setDocumentoDeIdentidad(aux.getDocumentoDeIdentidad());
        entity.setCelular(aux.getCelular());
        entity.setFechaNacimiento(aux.getFechaNacimiento());
        entity.setCorreo(aux.getCorreo());
        entity.setClave(aux.getClave());
        entity.setRol(aux.getRol());
        return entity;
    }

    public static Auxiliar_b toDomain(AuxEntity entity) {
        Auxiliar_b aux = new Auxiliar_b();
        aux.setNombre(entity.getNombre());
        aux.setApellido(entity.getApellido());
        aux.setDocumentoDeIdentidad(entity.getDocumentoDeIdentidad());
        aux.setCelular(entity.getCelular());
        aux.setFechaNacimiento(entity.getFechaNacimiento());
        aux.setCorreo(entity.getCorreo());
        aux.setClave(entity.getClave());
        aux.setRol(entity.getRol());
        return aux;
    }
}

