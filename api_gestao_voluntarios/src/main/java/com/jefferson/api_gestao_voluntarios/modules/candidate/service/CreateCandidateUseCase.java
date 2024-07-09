package com.jefferson.api_gestao_voluntarios.modules.candidate.service;

import com.jefferson.api_gestao_voluntarios.exceptions.UserFoundException;
import com.jefferson.api_gestao_voluntarios.modules.candidate.entities.CandidateEntity;
import com.jefferson.api_gestao_voluntarios.modules.candidate.entities.EnderecoEntityCandidate;
import com.jefferson.api_gestao_voluntarios.modules.candidate.repositories.CandidateRepository;
import com.jefferson.api_gestao_voluntarios.modules.candidate.repositories.EnderecoRepositoryCandidate;
import com.jefferson.api_gestao_voluntarios.modules.company.DTO.JobAndStatusDTO;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.CompanyEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.EnderecoEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobStatus;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.CandidateStatusRepository;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.CompanyRepository;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CreateCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EnderecoRepositoryCandidate enderecoRepositoryCandidate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CandidateStatusRepository candidateStatusRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private static final String UPLOAD_DIR = "C:\\rocketseat\\projeto-voluntario-conectado\\api_gestao_voluntarios\\imagens_profiles";


    public CandidateEntity execute(CandidateEntity candidate) {
        this.candidateRepository
                .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(candidate.getPassword());
        candidate.setPassword(password);

        return this.candidateRepository.save(candidate);
    }

    public CandidateEntity information(UUID id) {
        var candidate = this.candidateRepository.findById(id)
                .orElseThrow(()-> new RuntimeException("Não encontrado"));

        return candidate;

    }


    public String createEndereco(UUID idCompany, EnderecoEntityCandidate enderecoEntity) {
        try {
            CandidateEntity candidate = this.candidateRepository.findById(idCompany)
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            EnderecoEntityCandidate endereco = this.enderecoRepositoryCandidate.save(enderecoEntity);

            candidate.setEndereco(endereco);
            return "Endereço salvo com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public CandidateEntity uploadImage(UUID id, MultipartFile imageFile) throws IOException {
        CandidateEntity candidate = this.candidateRepository.findById(id).orElseThrow();

        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, imageFile.getBytes());

        candidate.setImagePath(filePath.toString());
        return this.candidateRepository.save(candidate);
    }

    public List<JobAndStatusDTO> getStatus(UUID id_candidate) {
        List<JobStatus> jobStatus= this.candidateStatusRepository.findBycandidateId(id_candidate);
        System.out.println(id_candidate);
        List<JobAndStatusDTO> lista = new ArrayList<>();
        jobStatus.forEach(item -> {
            var job = this.jobRepository.findById(item.getJobId())
                    .orElseThrow(() -> new RuntimeException("Job não encontrado!"));

            var company = this.companyRepository.findById(job.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company não encontrada"));
            JobAndStatusDTO meusStatus = new JobAndStatusDTO();
            meusStatus.setId_job(job.getId());
            meusStatus.setNome_instituicao(company.getName());
            meusStatus.setFuncao(job.getFuncao());
            meusStatus.setInicio(job.getInicio());
            meusStatus.setFim(job.getFim());
            meusStatus.setHorario_inicio(job.getHorario_inicio());
            meusStatus.setHorario_fim(job.getHorario_fim());
            meusStatus.setStatus(item.getStatus());

            lista.add(meusStatus);
        });



        return lista;
    }
}
