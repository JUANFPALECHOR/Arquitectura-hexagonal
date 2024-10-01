package com.pragma.arquetipobootcamp2024.domain.spi;


import com.pragma.arquetipobootcamp2024.domain.model.Usuario;

import java.util.Optional;

public interface IUsuarioPersistencePort {

    void save(Usuario usuario);
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByDocumentoDeIdentidad(String documentoDeIdentidad);

}
