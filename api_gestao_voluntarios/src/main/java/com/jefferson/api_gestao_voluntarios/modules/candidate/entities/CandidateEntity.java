package com.jefferson.api_gestao_voluntarios.modules.candidate.entities;

import com.jefferson.api_gestao_voluntarios.modules.company.entities.JobEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String username;

    @Email(message = "O campo deve conter um email válido")
    private String email;
    private String telefone;

    @Length(min = 10, max = 100, message = "O password deve conter entre (10) a (100) Caracteres")
    private String password;
    private String description;
    private String curriculum;

    private String imagePath;

    @ManyToMany(mappedBy = "candidates")
    private List<JobEntity> jobs;

    @OneToOne
    @JoinColumn(name = "endereco_id", referencedColumnName = "id")
    private EnderecoEntityCandidate endereco;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
