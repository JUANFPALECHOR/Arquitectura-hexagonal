package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.AuxEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AuxRepository extends JpaRepository<AuxEntity, Long> {
    Optional<AuxEntity> findByCorreo(String correo);
    Optional<AuxEntity> findByDocumentoDeIdentidad(String documentoDeIdentidad);

}
