package com.jefferson.api_gestao_voluntarios.modules.company.DTO;



import lombok.Data;
import org.springframework.core.io.Resource;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CompanyJobDTO {

    private UUID id_company;
    private String nomeInstituicao;
    private LocalDateTime createdAt;

    private UUID id;
    private String funcao;
    private String description;
    private String inicio;
    private String fim;
    private String horario_inicio;
    private String horario_fim;

}
