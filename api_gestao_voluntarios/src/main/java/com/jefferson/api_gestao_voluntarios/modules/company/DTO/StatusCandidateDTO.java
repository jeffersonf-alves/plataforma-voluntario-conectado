package com.jefferson.api_gestao_voluntarios.modules.company.DTO;


import lombok.Data;

import java.util.UUID;

@Data
public class StatusCandidateDTO {

    private String id_job;
    private String id_candidate;
    private String status;

}
