package com.jefferson.api_gestao_voluntarios.modules.company.DTO;


import com.jefferson.api_gestao_voluntarios.modules.company.entities.CompanyEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobEntity;

public class VagaDetailsMapper {

    public static VagaDetailsDTO getDatails(JobEntity job, CompanyEntity company) {
        VagaDetailsDTO vagaDetailsDTO = new VagaDetailsDTO();
        vagaDetailsDTO.setNome_instituicao(company.getName());
        vagaDetailsDTO.setRua(company.getEndereco().getRua());
        vagaDetailsDTO.setNumero(company.getEndereco().getNumero());
        vagaDetailsDTO.setBairro(company.getEndereco().getBairro());
        vagaDetailsDTO.setCidade(company.getEndereco().getCidade());
        vagaDetailsDTO.setEstado(company.getEndereco().getEstado());

        vagaDetailsDTO.setData_inicio(job.getInicio());
        vagaDetailsDTO.setData_fim(job.getFim());
        vagaDetailsDTO.setHora_inicio(job.getHorario_inicio());
        vagaDetailsDTO.setHora_fim(job.getHorario_fim());

        vagaDetailsDTO.setDescription(job.getDescription());

        return vagaDetailsDTO;
    }
}
