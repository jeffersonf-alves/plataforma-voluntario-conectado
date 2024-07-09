package com.jefferson.api_gestao_voluntarios.modules.company.DTO;


import lombok.Data;

@Data
public class VagaDetailsDTO {

    private String nome_instituicao;

    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;

    private String data_inicio;
    private String data_fim;

    private String hora_inicio;
    private String hora_fim;

    private String description;


}
