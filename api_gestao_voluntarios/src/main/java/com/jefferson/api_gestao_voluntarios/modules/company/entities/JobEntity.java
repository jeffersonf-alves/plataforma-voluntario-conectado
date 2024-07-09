package com.jefferson.api_gestao_voluntarios.modules.company.entities;


import com.jefferson.api_gestao_voluntarios.modules.candidate.entities.CandidateEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity(name = "job")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String funcao;
    private String description;
    private String inicio;
    private String fim;
    private String horario_inicio;
    private String horario_fim;

    @ManyToOne()
    @JoinColumn(name = "company_id", insertable=false, updatable=false)
    private CompanyEntity companyEntity;

    @Column(name = "company_id")
    private UUID companyId;

    @ManyToMany
    @JoinTable(name = "job_candidato",
                joinColumns = @JoinColumn(name = "job_fk"),
                inverseJoinColumns = @JoinColumn(name = "candidate_fk"))
    private List<CandidateEntity> candidates;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
