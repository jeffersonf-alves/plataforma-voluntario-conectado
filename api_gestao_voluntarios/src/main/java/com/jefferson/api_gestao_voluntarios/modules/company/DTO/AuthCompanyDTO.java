package com.jefferson.api_gestao_voluntarios.modules.company.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthCompanyDTO {

    private String password;
    private String username;
}
