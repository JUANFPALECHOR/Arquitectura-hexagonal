package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;


import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.UsuarioEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.mapper.UsuarioMapper;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.UsuarioRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Usuario;
import com.pragma.arquetipobootcamp2024.domain.spi.IUsuarioPersistencePort;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class UsuarioAdapter implements IUsuarioPersistencePort {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioMapper usuarioMapper;

    @Autowired
    public UsuarioAdapter(UsuarioRepository usuarioRepository,
                                     UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Override
    public void save(Usuario usuario) {
        UsuarioEntity usuarioEntity = usuarioMapper.toEntity(usuario);
        usuarioRepository.save(usuarioEntity);
    }

    @Override
    public Optional<Usuario> findByCorreo(String correo) {
        Optional<UsuarioEntity> usuarioEntityOpt = usuarioRepository.findByCorreo(correo);
        return usuarioEntityOpt.map(usuarioMapper::toDomain);
    }

    @Override
    public Optional<Usuario> findByDocumentoDeIdentidad(String documentoDeIdentidad) {
        Optional<UsuarioEntity> usuarioEntityOpt = usuarioRepository.findByDocumentoDeIdentidad(documentoDeIdentidad);
        return usuarioEntityOpt.map(usuarioMapper::toDomain);
    }
}


