package com.jefferson.api_gestao_voluntarios.modules.company.mappers;


import lombok.Data;

import java.util.UUID;

@Data
public class CandidateAndStatusDTO {

    private UUID jobId;
    private UUID statusId;
    private UUID candidadeId;
    private String funcao;
    private String name;
    private String telefone;
    private String email;
    private String status;


}
