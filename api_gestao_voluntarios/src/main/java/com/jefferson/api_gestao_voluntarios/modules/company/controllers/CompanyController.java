package com.jefferson.api_gestao_voluntarios.modules.company.controllers;

import com.jefferson.api_gestao_voluntarios.modules.company.DTO.GetIdDTO;
import com.jefferson.api_gestao_voluntarios.modules.company.DTO.LinksCompanyDTO;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.CompanyEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.entities.EnderecoEntity;
import com.jefferson.api_gestao_voluntarios.modules.company.service.CreateCompanyUseCase;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@RestController
@RequestMapping("/company")
public class CompanyController {

    @Autowired
    private CreateCompanyUseCase createCompanyUseCase;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody CompanyEntity companyEntity) {
        try {
            var result = createCompanyUseCase.execute(companyEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/info")
    public ResponseEntity<Object> info(HttpServletRequest request) {
        try {
            var companyId = request.getAttribute("company_id");
            var result = this.createCompanyUseCase.information(UUID.fromString(companyId.toString()));

            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @PostMapping("/imagem")
    public ResponseEntity<Object> uploadImage(@RequestParam("image") MultipartFile imageFile, HttpServletRequest request)  {
        try {
            var companyId = request.getAttribute("company_id");
            this.createCompanyUseCase.uploadImage(UUID.fromString(companyId.toString()), imageFile);
            return ResponseEntity.ok().body("Imagem salva com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/profile/avatar")
    public ResponseEntity<Object> getAvatar(HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");
        Resource resource = this.createCompanyUseCase.getAvatar(UUID.fromString(companyId.toString()));
        String contentType;
        try {
            contentType = Files.probeContentType(Paths.get(resource.getURI()));
        } catch (Exception e) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }



    @PostMapping("/links")
    public ResponseEntity<Object> saveLinks(@RequestBody LinksCompanyDTO linksCompanyDTO, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");
         try {
             var result = this.createCompanyUseCase
                     .saveLinks(UUID.fromString(companyId.toString()), linksCompanyDTO);
             return ResponseEntity.ok().body(result);
         } catch (Exception e) {
             return ResponseEntity.badRequest().body(e.getMessage());
         }
    }

    @PostMapping("/endereco")
    public ResponseEntity<Object> saveEndereco(@RequestBody EnderecoEntity enderecoEntity, HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");
        try {
            var result = this.createCompanyUseCase
                    .createEndereco(UUID.fromString(companyId.toString()), enderecoEntity);
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/vagas/lista")
    public ResponseEntity<Object> getListaVagas(HttpServletRequest request) {
        var companyId = request.getAttribute("company_id");
        try {
            var result = this.createCompanyUseCase
                    .getVagas(UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/participate")
    public ResponseEntity<Object> getStatus(HttpServletRequest request) {
        try {
            var companyId = request.getAttribute("company_id");
            var result = this.createCompanyUseCase.getStatus(UUID.fromString(companyId.toString()));
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/candidate/profile")
    public ResponseEntity<Object> getCandidate(@RequestBody GetIdDTO id) {
        try {
            var result = this.createCompanyUseCase.getCandidateDetails(id.getId());
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }



}
