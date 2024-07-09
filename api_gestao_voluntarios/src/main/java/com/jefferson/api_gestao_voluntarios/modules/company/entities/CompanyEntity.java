package com.jefferson.api_gestao_voluntarios.modules.company.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.UUID;


@Entity(name = "company")
@Data
public class CompanyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String username;
    private String cnpj;

    // CONTATOS
    private String telefone;
    @Email(message = "O campo deve conter um email válido")
    private String email;

    @Length(min = 10, max = 100, message = "O password deve conter entre (10) a (100) Caracteres")
    private String password;
    private String description;

    // REDES SOCIAIS
    private String website;
    private String facebook;
    private String instagram;
    private String linkedin;


    private String imagePath;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private EnderecoEntity endereco;
}
