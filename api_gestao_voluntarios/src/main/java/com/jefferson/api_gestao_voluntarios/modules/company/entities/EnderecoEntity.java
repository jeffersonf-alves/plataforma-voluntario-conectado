package com.jefferson.api_gestao_voluntarios.modules.company.entities;


import jakarta.persistence.*;
import lombok.Data;


import java.util.UUID;

@Entity
@Table(name = "endereco")
@Data
public class EnderecoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String rua;
    private String numero;
    private String bairro;
    private String cidade;
    private String estado;
    private String cep;


}
