package com.jefferson.api_gestao_voluntarios.modules.company.DTO;


import com.jefferson.api_gestao_voluntarios.modules.company.entities.CompanyEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobEntity;

public class CompanyAndJobMapper {

    public static CompanyJobDTO toCreateJobDTO(JobEntity jobEntity, CompanyEntity companyEntity) {
        CompanyJobDTO dto = new CompanyJobDTO();
        dto.setId_company(companyEntity.getId());
        dto.setNomeInstituicao(companyEntity.getName());
        dto.setCreatedAt(companyEntity.getCreatedAt());

        dto.setId(jobEntity.getId());
        dto.setFuncao(jobEntity.getFuncao());
        dto.setDescription(jobEntity.getDescription());
        dto.setInicio(jobEntity.getInicio());
        dto.setFim(jobEntity.getFim());
        dto.setHorario_inicio(jobEntity.getHorario_inicio());
        dto.setHorario_fim(jobEntity.getHorario_fim());

        return dto;
    }
}
