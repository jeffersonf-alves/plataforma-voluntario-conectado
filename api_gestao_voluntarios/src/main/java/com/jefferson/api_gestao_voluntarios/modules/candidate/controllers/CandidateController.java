package com.jefferson.api_gestao_voluntarios.modules.candidate.controllers;

import com.jefferson.api_gestao_voluntarios.modules.candidate.entities.CandidateEntity;
import com.jefferson.api_gestao_voluntarios.modules.candidate.entities.EnderecoEntityCandidate;
import com.jefferson.api_gestao_voluntarios.modules.candidate.service.CreateCandidateUseCase;
import com.jefferson.api_gestao_voluntarios.modules.candidate.service.ProfileCandidateUseCase;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.EnderecoEntity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/candidate")
public class CandidateController {

    @Autowired
    private CreateCandidateUseCase createCandidateUseCase;

    @Autowired
    private ProfileCandidateUseCase profileCandidateUseCase;

    @PostMapping("")
    public ResponseEntity<Object> create(@Valid @RequestBody CandidateEntity candidate) {
        try {
            var result = this.createCandidateUseCase.execute(candidate);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Object> info(HttpServletRequest request) {
        try {
            var idCandidate = request.getAttribute("candidate_id");
            var result = this.createCandidateUseCase.information(UUID.fromString(idCandidate.toString()));

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public ResponseEntity<Object> getCandidate(HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var profile = this.profileCandidateUseCase.execute(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(profile);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/endereco")
    public ResponseEntity<Object> saveEndereco(@RequestBody EnderecoEntityCandidate enderecoEntity, HttpServletRequest request) {
        var idCandidate = request.getAttribute("candidate_id");
        try {
            var result = this.createCandidateUseCase
                    .createEndereco(UUID.fromString(idCandidate.toString()), enderecoEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/imagem")
    public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile imageFile, HttpServletRequest request)  {
        try {
            var companyId = request.getAttribute("company_id");
            this.createCandidateUseCase.uploadImage(UUID.fromString(companyId.toString()), imageFile);
            return ResponseEntity.ok().body("Imagem salva com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/participate")
    public ResponseEntity<Object> getStatus(HttpServletRequest request) {
        try {
            var idCandidate = request.getAttribute("candidate_id");
            var result = this.createCandidateUseCase.getStatus(UUID.fromString(idCandidate.toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
