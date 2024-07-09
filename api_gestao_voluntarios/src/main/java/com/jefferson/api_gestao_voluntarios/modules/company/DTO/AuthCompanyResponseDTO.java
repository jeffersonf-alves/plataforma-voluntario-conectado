package com.jefferson.api_gestao_voluntarios.modules.company.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthCompanyResponseDTO {

    private String access_token;
    private Long expires_in;
    private UUID id;
}
