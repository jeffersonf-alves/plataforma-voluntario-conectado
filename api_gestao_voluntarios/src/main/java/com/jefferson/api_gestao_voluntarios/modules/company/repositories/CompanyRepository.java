package com.jefferson.api_gestao_voluntarios.modules.company.repositories;

import com.jefferson.api_gestao_voluntarios.modules.company.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, UUID> {
    Optional<CompanyEntity> findByUsernameOrEmail(String username, String email);
    Optional<CompanyEntity> findByUsername(String username);

}
