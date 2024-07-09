package com.jefferson.api_gestao_voluntarios.modules.company.service;

import com.jefferson.api_gestao_voluntarios.exceptions.UserFoundException;
import com.jefferson.api_gestao_voluntarios.modules.candidate.entities.CandidateEntity;
import com.jefferson.api_gestao_voluntarios.modules.candidate.repositories.CandidateRepository;
import com.jefferson.api_gestao_voluntarios.modules.company.DTO.CreateJobDTO;
import com.jefferson.api_gestao_voluntarios.modules.company.DTO.JobAndStatusDTO;
import com.jefferson.api_gestao_voluntarios.modules.company.DTO.JobMapper;
import com.jefferson.api_gestao_voluntarios.modules.company.DTO.LinksCompanyDTO;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.CompanyEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.EnderecoEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobStatus;
import com.jefferson.api_gestao_voluntarios.modules.company.mappers.CandidateAndStatusDTO;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.CandidateStatusRepository;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.CompanyRepository;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.EnderecoRepository;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private CandidateStatusRepository candidateStatusRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    private static final String UPLOAD_DIR = "C:\\rocketseat\\projeto-voluntario-conectado\\api_gestao_voluntarios\\imagens_profiles";
    @Autowired
    private PasswordEncoder passwordEncoder;

    public CompanyEntity execute(CompanyEntity companyEntity) {
        this.companyRepository
                .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((user) -> {
                    throw new UserFoundException();
                });
        var password = passwordEncoder.encode(companyEntity.getPassword());
        companyEntity.setPassword(password);
        return this.companyRepository.save(companyEntity);
    }

    public Optional<CompanyEntity> information(UUID id) {
        return this.companyRepository.findById(id);
    }

    public CompanyEntity uploadImage(UUID id, MultipartFile imageFile) throws IOException {
        CompanyEntity candidate = companyRepository.findById(id).orElseThrow();

        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR, fileName);
        Files.createDirectories(filePath.getParent());
        Files.write(filePath, imageFile.getBytes());

        candidate.setImagePath(filePath.toString());
        return companyRepository.save(candidate);
    }

    public Resource getAvatar(UUID id) {
        CompanyEntity company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        try {
            Path filePath = Paths.get(company.getImagePath()).toAbsolutePath().normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Arquivo não encontrado: " + company.getImagePath());
            }
        } catch (Exception e) {
            throw new RuntimeException("Arquivo não permitido: ", e);
        }
    }

    public String saveLinks(UUID id, LinksCompanyDTO linksCompanyDTO) {

        try {
            CompanyEntity company = companyRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            company.setFacebook(linksCompanyDTO.getFacebook());
            company.setInstagram(linksCompanyDTO.getInstagram());
            company.setLinkedin(linksCompanyDTO.getLinkedin());
            company.setWebsite(linksCompanyDTO.getWebsite());
            this.companyRepository.save(company);
            return "Salvo com sucesso!";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public String createEndereco(UUID idCompany, EnderecoEntity enderecoEntity) {
        try {
            CompanyEntity company = this.companyRepository.findById(idCompany)
                    .orElseThrow(() -> new RuntimeException("Company not found"));
            EnderecoEntity endereco = this.enderecoRepository.save(enderecoEntity);

            company.setEndereco(endereco);
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }

    }

    public List<CreateJobDTO> getVagas(UUID id) {
        List<JobEntity> lista = this.jobRepository.findBycompanyId(id);


        return lista.stream()
                .map(JobMapper::toCreateJobDTO)
                .collect(Collectors.toList());
    }

    public List<CandidateAndStatusDTO> getStatus(UUID idCompany) {
        List<JobStatus> jobStatus= this.candidateStatusRepository.findBycompanyId(idCompany);

        List<CandidateAndStatusDTO> lista = new ArrayList<>();
        jobStatus.forEach(item -> {
            var job = this.jobRepository.findById(item.getJobId())
                    .orElseThrow(() -> new RuntimeException("Job não encontrado!"));

            var candidate = this.candidateRepository.findById(item.getCandidateId())
                    .orElseThrow(() -> new RuntimeException("Company não encontrada"));
            CandidateAndStatusDTO candidateStatus = new CandidateAndStatusDTO();
            candidateStatus.setStatusId(item.getId());
            candidateStatus.setJobId(job.getId());
            candidateStatus.setCandidadeId(candidate.getId());
            candidateStatus.setName(candidate.getName());
            candidateStatus.setTelefone(candidate.getTelefone());
            candidateStatus.setEmail(candidate.getEmail());
            candidateStatus.setFuncao(job.getFuncao());
            candidateStatus.setStatus(item.getStatus());

            lista.add(candidateStatus);
        });

        return lista;
    }

    public CandidateEntity getCandidateDetails(UUID id) {
        CandidateEntity candidate = this.candidateRepository.findById(id).orElseThrow();
        return candidate;
    }

//    public CandidateAndStatusDTO getCandidateAndStatus(UUID Idcompany) {
//        var ListJobs = this.jobRepository.findBycompanyId(Idcompany);
//
//        ListJobs.forEach((item) -> {
//            var jobStatus = this.candidateStatusRepository
//        });
//    }

}
