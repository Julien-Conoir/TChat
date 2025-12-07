package com.alom.push.client;

import com.alom.push.dto.TokenValidationResponseDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class AuthClient {

    private final WebClient webClient;

    public AuthClient(@Value("${auth.service.url:http://localhost:8081}") String authServiceUrl) {
        this.webClient = WebClient.builder()
                .baseUrl(authServiceUrl)
                .build();
    }

    public TokenValidationResponseDTO validateToken(String token) {
        return webClient.post()
                .uri("/auth/token")
                .header("Authorization", token)
                .retrieve()
                .bodyToMono(TokenValidationResponseDTO.class)
                .block();
    }
}