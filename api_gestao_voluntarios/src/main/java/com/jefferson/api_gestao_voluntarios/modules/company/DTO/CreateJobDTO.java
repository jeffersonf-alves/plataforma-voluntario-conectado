package com.jefferson.api_gestao_voluntarios.modules.company.DTO;


import lombok.Data;

import java.util.UUID;

@Data
public class CreateJobDTO {

    private UUID id;
    private String funcao;
    private String description;
    private String inicio;
    private String fim;
    private String horario_inicio;
    private String horario_fim;

}
