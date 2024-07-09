package com.jefferson.api_gestao_voluntarios.modules.company.service;


import com.jefferson.api_gestao_voluntarios.modules.candidate.entities.CandidateEntity;
import com.jefferson.api_gestao_voluntarios.modules.candidate.repositories.CandidateRepository;
import com.jefferson.api_gestao_voluntarios.modules.company.DTO.*;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.CompanyEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobStatus;
import com.jefferson.api_gestao_voluntarios.modules.company.mappers.CandidateAndStatusDTO;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.CandidateStatusRepository;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.CompanyRepository;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.JobRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateJobUseCase {

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CandidateStatusRepository candidateStatusRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public JobEntity execute(JobEntity jobEntity) {
        return this.jobRepository.save(jobEntity);
    }

    public String subscribeCandidate(UUID id_job, UUID id_candidate) {
        JobEntity job = this.jobRepository.findById(id_job).orElseThrow(
                () -> new RuntimeException("job not found"));
        CandidateEntity candidate = this.candidateRepository.findById(id_candidate).orElseThrow(
                () -> new RuntimeException("candidate not found"));

        job.getCandidates().add(candidate);

        this.jobRepository.save(job);
        return "Cadastro feito com sucesso!";
    }

    public List<CompanyJobDTO> getAll() {
        List<JobEntity> jobList = this.jobRepository.findAll();

        return jobList.stream()
                .map(job -> {
                    CompanyEntity company = this.companyRepository.findById(job.getCompanyId())
                            .orElseThrow(() -> new EntityNotFoundException("Company not found"));
                    return CompanyAndJobMapper.toCreateJobDTO(job, company);
                })
                .collect(Collectors.toList());
    }

    public VagaDetailsDTO getVagaDetails(UUID idCompany, UUID idJob) {
        var company = this.companyRepository.findById(idCompany)
                .orElseThrow(() -> new RuntimeException("Company não encontrada"));
        var job = this.jobRepository.findById(idJob)
                .orElseThrow(() -> new RuntimeException("Job não encontrado"));

        return VagaDetailsMapper.getDatails(job, company);
    }

    public String cadastrarStatus(UUID idJob, UUID idCandidate, String status) {
        var job = this.jobRepository.findById(idJob).orElseThrow();
        var company = this.companyRepository.findById(job.getCompanyId()).orElseThrow();
        try {
            JobStatus newStatus = new JobStatus();
            newStatus.setCompanyId(company.getId());
            newStatus.setJobId(idJob);
            newStatus.setCandidateId(idCandidate);
            newStatus.setStatus(status);
            this.candidateStatusRepository.save(newStatus);
            return "Cadastrado com sucesso!";
        } catch (Exception e) {
            return "Erro ao cadastrar: "+ e.getMessage();
        }
    }

    public JobStatus atualizaStatus(UUID id, String status) {
        JobStatus jobStatus = this.candidateStatusRepository.findById(id).orElseThrow();
        jobStatus.setStatus(status);

        return this.candidateStatusRepository.save(jobStatus);
    }

}

