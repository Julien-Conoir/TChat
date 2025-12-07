package com.alom.push.service;

import com.alom.push.client.AuthClient;
import com.alom.push.dto.TokenValidationResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class TokenService {
    
    private final AuthClient authClient;

    public TokenValidationResponseDTO isTokenValid(String token) {
        try {
            return this.authClient.validateToken(token);
        } catch (WebClientResponseException e) {
            log.error("Erreur lors de la validation du token: {}", e.getMessage());
            return null;
        } catch (Exception e) {
            log.error("Erreur inattendue lors de la validation du token: {}", e.getMessage(), e);
            return null;
        }
    }
}