package com.jefferson.api_gestao_voluntarios.modules.candidate.service;


import com.jefferson.api_gestao_voluntarios.modules.candidate.DTO.ProfileCandidateResponseDTO;
import com.jefferson.api_gestao_voluntarios.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidato) {
        var candidate = this.candidateRepository.findById(idCandidato)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("User not found");
                });
        var candidateDTO = ProfileCandidateResponseDTO.builder()
                .id(candidate.getId())
                .name(candidate.getName())
                .username(candidate.getUsername())
                .description(candidate.getDescription())
                .curriculum(candidate.getCurriculum())
                .build();
        return candidateDTO;
    }
}
