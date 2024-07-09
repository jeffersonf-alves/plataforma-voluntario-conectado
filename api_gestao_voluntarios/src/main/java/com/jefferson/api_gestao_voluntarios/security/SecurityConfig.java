package com.jefferson.api_gestao_voluntarios.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig  {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:8080", "http://192.168.15.11:8080"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Autowired
    private SecurityFilter securityFilter;

    @Autowired
    private SecurityCandidateFilter securityCandidateFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(c -> c.configurationSource(corsConfigurationSource()).disable())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> {
                        auth.requestMatchers("/candidate").permitAll()
                                .requestMatchers("/job/create").permitAll()
                                .requestMatchers("/job/all").permitAll()
                                .requestMatchers("/job/status").permitAll()
                                .requestMatchers("/job/candidate/status").permitAll()
                                .requestMatchers("/candidate/participate").permitAll()
                                .requestMatchers("/company/participate").permitAll()
                                .requestMatchers("/company/candidate/profile").permitAll()
                                .requestMatchers("/job/vaga/details").permitAll()
                                .requestMatchers("/company/info").permitAll()
                                .requestMatchers("/candidate/info").permitAll()
                                .requestMatchers("/company/profile/avatar").permitAll()
                                .requestMatchers("/candidate/auth").permitAll()
                                .requestMatchers("/job/new-candidate").permitAll()
                                .requestMatchers("/company/imagem").permitAll()
                                .requestMatchers("/company/vagas/lista").permitAll()
                                .requestMatchers("/company/links").permitAll()
                                .requestMatchers("/company/auth").permitAll();
                        auth.anyRequest().authenticated();
                })
                .addFilterBefore(securityFilter, BasicAuthenticationFilter.class)
                .addFilterBefore(securityCandidateFilter, BasicAuthenticationFilter.class);

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
