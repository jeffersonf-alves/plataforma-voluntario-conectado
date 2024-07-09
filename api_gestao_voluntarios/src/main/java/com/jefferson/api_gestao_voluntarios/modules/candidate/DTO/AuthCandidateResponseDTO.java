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
public class AuthCandidateResponseDTO {

    private String access_token;
    private UUID idCandidate;
    private Long expires_in;
}
