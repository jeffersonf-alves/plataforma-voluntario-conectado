package com.jefferson.api_gestao_voluntarios.modules.company.DTO;


import lombok.Data;

import java.util.UUID;

@Data
public class JobAndStatusDTO {
    private UUID id_job;
    private String nome_instituicao;
    private String funcao;
    private String inicio;
    private String fim;
    private String horario_inicio;
    private String horario_fim;
    private String status;
}
