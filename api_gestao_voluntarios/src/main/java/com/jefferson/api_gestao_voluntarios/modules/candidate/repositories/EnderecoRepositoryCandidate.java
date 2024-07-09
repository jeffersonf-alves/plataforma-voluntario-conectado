package com.jefferson.api_gestao_voluntarios.modules.candidate.repositories;


import com.jefferson.api_gestao_voluntarios.modules.candidate.entities.EnderecoEntityCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EnderecoRepositoryCandidate extends JpaRepository<EnderecoEntityCandidate, UUID> {

}
