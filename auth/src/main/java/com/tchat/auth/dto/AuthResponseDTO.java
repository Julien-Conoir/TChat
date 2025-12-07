package com.tchat.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class AuthResponseDTO {

    @Schema(description = "User's nickname", example = "alice")
    private String nickname;

    @Schema(description = "Authentication token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9")
    private String token;
}