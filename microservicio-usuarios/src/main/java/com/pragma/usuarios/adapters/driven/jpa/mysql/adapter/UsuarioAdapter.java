package com.pragma.usuarios.adapters.driven.jpa.mysql.adapter;


import com.pragma.usuarios.adapters.driven.jpa.mysql.entity.UsuarioEntity;
import com.pragma.usuarios.adapters.driven.jpa.mysql.mapper.UsuarioMapper;
import com.pragma.usuarios.adapters.driven.jpa.mysql.repository.UsuarioRepository;
import com.pragma.usuarios.domain.model.Usuario;
import com.pragma.usuarios.domain.spi.IUsuarioPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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


