package com.jefferson.api_gestao_voluntarios.modules.company.entities;


import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
@Table(name = "job_status")
public class JobStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID companyId;
    private UUID jobId;
    private UUID candidateId;
    private String status;
}
