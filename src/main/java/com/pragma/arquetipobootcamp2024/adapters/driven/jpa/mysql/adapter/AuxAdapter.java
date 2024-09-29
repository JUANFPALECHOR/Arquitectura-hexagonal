package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;


import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.AuxEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.AuxMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.AuxRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Auxiliar_b;
import com.pragma.arquetipobootcamp2024.domain.spi.IAuxRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;



@Component
public class AuxAdapter implements IAuxRepository {

    private final AuxRepository auxRepository;
    private final AuxMapper auxMapper; // Asumiendo que tienes un mapper para Aux

    public AuxAdapter(AuxRepository auxRepository, AuxMapper auxMapper) {
        this.auxRepository = auxRepository;
        this.auxMapper = auxMapper;
    }

    @Override
    public void save(Auxiliar_b aux) {
        // Convierte el modelo de dominio a la entidad y guarda en la base de datos
        AuxEntity auxEntity = auxMapper.toEntity(aux);
        auxRepository.save(auxEntity);
    }

    @Override
    public Optional<Auxiliar_b> findByCorreo(String correo) {
        return auxRepository.findByCorreo(correo)
                .map(auxMapper::toDomain); // Convierte de AuxEntity a Auxiliar_b
    }

    @Override
    public Optional<Auxiliar_b> findByDocumentoDeIdentidad(String documentoDeIdentidad) {
        return auxRepository.findByDocumentoDeIdentidad(documentoDeIdentidad)
                .map(auxMapper::toDomain); // Convierte de AuxEntity a Auxiliar_b
    }
}


