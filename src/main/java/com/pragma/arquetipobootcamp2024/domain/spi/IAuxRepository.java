package com.pragma.arquetipobootcamp2024.domain.spi;


import com.pragma.arquetipobootcamp2024.domain.model.Auxiliar_b;

import java.util.Optional;

public interface IAuxRepository {


    void save(Auxiliar_b aux);

    Optional<Auxiliar_b> findByCorreo(String correo);
    Optional<Auxiliar_b> findByDocumentoDeIdentidad(String documentoDeIdentidad);

}
