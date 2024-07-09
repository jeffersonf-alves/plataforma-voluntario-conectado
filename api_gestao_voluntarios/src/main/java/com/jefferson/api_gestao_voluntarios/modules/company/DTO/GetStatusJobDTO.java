package com.jefferson.api_gestao_voluntarios.modules.company.DTO;

import lombok.Data;

import java.util.UUID;

@Data
public class GetStatusJobDTO {

    private UUID id;
    private String status;
}
