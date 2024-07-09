package com.jefferson.api_gestao_voluntarios.modules.company.service;

import javax.naming.AuthenticationException;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.jefferson.api_gestao_voluntarios.modules.company.DTO.AuthCompanyDTO;
import com.jefferson.api_gestao_voluntarios.modules.company.DTO.AuthCompanyResponseDTO;
import com.jefferson.api_gestao_voluntarios.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;


@Service
public class AuthCompanyUseCase {

    @Value("${security.token.secret}")
    private String secretKey;
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthCompanyResponseDTO execute(AuthCompanyDTO companyDTO) throws AuthenticationException {
        var company = this.companyRepository.findByUsername(companyDTO.getUsername())
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException("Company not found");
                });
        var passwordMatchers = this.passwordEncoder.matches(companyDTO.getPassword(), company.getPassword());

        if(!passwordMatchers) {
            throw new AuthenticationException();
        }

        Algorithm algorithm = Algorithm.HMAC256(secretKey);
        var expiresIn = Instant.now().plus(Duration.ofHours(2));

        var token = JWT.create().withIssuer("vol_vagas")
                .withExpiresAt(Instant.now().plus(Duration.ofHours(2)))
                .withSubject(company.getId().toString())
                .withExpiresAt(expiresIn)
                .withClaim("roles", Arrays.asList("COMPANY"))
                .sign(algorithm);

        var authCompanyResponseDTO = AuthCompanyResponseDTO.builder()
                .access_token(token)
                .expires_in(expiresIn.toEpochMilli())
                .id(company.getId())
                .build();

        return authCompanyResponseDTO;
    }
}
