package com.jefferson.api_gestao_voluntarios.modules.candidate.service;

import javax.naming.AuthenticationException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jefferson.api_gestao_voluntarios.modules.candidate.DTO.AuthCandidateRequestDTO;
import com.jefferson.api_gestao_voluntarios.modules.candidate.DTO.AuthCandidateResponseDTO;
import com.jefferson.api_gestao_voluntarios.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

@Service
public class AuthCandidateUseCase {

    @Value("${security.token.secret.candidate}")
    private String secretKey;
    @Autowired
    private CandidateRepository candidateRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
        var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.getUsername())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Username/Password Incorret");
                });
        var passwordMatches = this.passwordEncoder
                .matches(authCandidateRequestDTO.getPassword(), candidate.getPassword());

        if(!passwordMatches) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("vol_vagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(candidate.getId().toString())
                .withExpiresAt(expiresIn)
                .withClaim("roles", Arrays.asList("CANDIDATE"))
                .sign(algorithm);



        var authCandidateResponse = AuthCandidateResponseDTO
                .builder()
                .access_token(token)
                .idCandidate(candidate.getId())
                .expires_in(expiresIn.toEpochMilli())
                .build();



        return authCandidateResponse;


    }
}
