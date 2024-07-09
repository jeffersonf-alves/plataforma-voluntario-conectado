package com.jefferson.api_gestao_voluntarios.modules.candidate.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCandidateRequestDTO{

    private String password;
    private String username;
}
