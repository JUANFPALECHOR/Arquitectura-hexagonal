package com.pragma.usuarios.domain.spi;


import com.pragma.usuarios.domain.model.Usuario;

import java.util.Optional;

public interface IUsuarioPersistencePort {

    void save(Usuario usuario);
    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByDocumentoDeIdentidad(String documentoDeIdentidad);

}
