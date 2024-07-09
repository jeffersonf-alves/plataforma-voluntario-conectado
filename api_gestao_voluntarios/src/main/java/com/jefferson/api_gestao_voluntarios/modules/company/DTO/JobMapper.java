package com.jefferson.api_gestao_voluntarios.modules.company.DTO;

import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobEntity;

public class JobMapper {

    public static CreateJobDTO toCreateJobDTO(JobEntity jobEntity) {
        CreateJobDTO dto = new CreateJobDTO();
        dto.setId(jobEntity.getId());
        dto.setFuncao(jobEntity.getFuncao());
        dto.setDescription(jobEntity.getDescription());
        dto.setInicio(jobEntity.getInicio());
        dto.setFim(jobEntity.getFim());
        dto.setHorario_inicio(jobEntity.getHorario_inicio());
        dto.setHorario_fim(jobEntity.getHorario_fim());
        // mapear outros campos conforme necessário
        return dto;
    }
}
