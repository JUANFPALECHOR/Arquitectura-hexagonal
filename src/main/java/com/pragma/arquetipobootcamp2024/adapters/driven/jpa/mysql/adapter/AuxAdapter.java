package com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.adapter;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.entity.AuxEntity;
import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.repository.AuxRepository;
import com.pragma.arquetipobootcamp2024.domain.model.Auxiliar_b;

public class AuxAdapter {

    private final AuxRepository auxRepository;

    public AuxAdapter(AuxRepository auxRepository) {
        this.auxRepository = auxRepository;

    }


}

