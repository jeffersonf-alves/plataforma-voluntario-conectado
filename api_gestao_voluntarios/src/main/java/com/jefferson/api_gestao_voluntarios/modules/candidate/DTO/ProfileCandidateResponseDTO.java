package com.jefferson.api_gestao_voluntarios.modules.candidate.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {
    
    private UUID id;
    private String username;
    private String name;
    private String description;
    private String curriculum;
}
