package com.pragma.usuarios.adapters.driven.jpa.mysql.repository;

import com.pragma.usuarios.adapters.driven.jpa.mysql.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByCorreo(String correo);
    Optional<UsuarioEntity> findByDocumentoDeIdentidad(String documentoDeIdentidad);

}
