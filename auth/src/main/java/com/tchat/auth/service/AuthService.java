package com.tchat.auth.service;

import com.tchat.auth.dto.AuthRequestDTO;
import com.tchat.auth.dto.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO register(AuthRequestDTO request);
    AuthResponseDTO login(AuthRequestDTO request);
    AuthResponseDTO isTokenValid(String token);
}