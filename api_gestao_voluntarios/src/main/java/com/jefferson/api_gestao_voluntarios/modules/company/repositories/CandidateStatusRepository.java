package com.jefferson.api_gestao_voluntarios.modules.company.repositories;


import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CandidateStatusRepository extends JpaRepository<JobStatus, UUID> {

    List<JobStatus> findBycandidateId(UUID candidateId);
    List<JobStatus> findBycompanyId(UUID companyId);

}
