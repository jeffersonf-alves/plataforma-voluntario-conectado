package com.jefferson.api_gestao_voluntarios.modules.company.repositories;


import com.jefferson.api_gestao_voluntarios.modules.company.entities.EnderecoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnderecoRepository extends JpaRepository<EnderecoEntity, UUID> {
}
