package com.tchat.gateway.dto;

import lombok.Data;

@Data
public class AuthResponseDTO {
    private String nickname;
    private String token;
}