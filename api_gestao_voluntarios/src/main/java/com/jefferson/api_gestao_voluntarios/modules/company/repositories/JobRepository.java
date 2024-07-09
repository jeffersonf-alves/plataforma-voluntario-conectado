package com.jefferson.api_gestao_voluntarios.modules.company.repositories;


import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface JobRepository extends JpaRepository<JobEntity, UUID> {

    List<JobEntity> findBycompanyId(UUID companyId);
}
