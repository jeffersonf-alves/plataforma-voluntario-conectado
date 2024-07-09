package com.jefferson.api_gestao_voluntarios.modules.company.controllers;


import com.jefferson.api_gestao_voluntarios.modules.company.DTO.*;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobStatus;
import com.jefferson.api_gestao_voluntarios.modules.company.service.CreateJobUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/job")
public class JobController {

    @Autowired
    private CreateJobUseCase createJobUseCase;

    @PostMapping("/create/{id}")
    public ResponseEntity<Object> create(@PathVariable("id") String id, @Valid @RequestBody CreateJobDTO createJobDTO) {
        try {
//            jobEntity.setCompanyId(UUID.fromString(companyId.toString()));
            var jobEntity = JobEntity.builder()
                    .description(createJobDTO.getDescription())
                    .inicio(createJobDTO.getInicio())
                    .fim(createJobDTO.getFim())
                    .funcao(createJobDTO.getFuncao())
                    .horario_inicio(createJobDTO.getHorario_inicio())
                    .horario_fim(createJobDTO.getHorario_fim())
                    .companyId(UUID.fromString(id))
                    .build();

            var result =this.createJobUseCase.execute(jobEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }

    @PostMapping("/new-candidate")
    public ResponseEntity<Object> newCandidate(@RequestBody GetIdDTO id, HttpServletRequest request) {

        var idCandidate = request.getAttribute("candidate_id");
        try {
            var result = this.createJobUseCase.subscribeCandidate(id.getId(), UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body("");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }

    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll() {
        try {
            var result = this.createJobUseCase.getAll();
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/vaga/details")
    public ResponseEntity<Object> getId(@RequestBody JobIdAndCompanyIdDTO jodAndCompanyID) {
        try {
            var result = this.createJobUseCase
                    .getVagaDetails(UUID.fromString(jodAndCompanyID.getId_company().toString()), UUID.fromString(jodAndCompanyID.getId_job().toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/status")
    public ResponseEntity<Object> cadastroVaga(@RequestBody StatusCandidateDTO statusCandidateDTO) {
        try {
            var result = this.createJobUseCase
                    .cadastrarStatus(UUID.fromString(statusCandidateDTO.getId_job()),
                            UUID.fromString(statusCandidateDTO.getId_candidate()),
                            statusCandidateDTO.getStatus());
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/status")
    public ResponseEntity<Object> atualizaStatus(@RequestBody GetStatusJobDTO newStatus) {
        try {
            var result = this.createJobUseCase.atualizaStatus(newStatus.getId(), newStatus.getStatus());

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }



}
