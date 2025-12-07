package com.tchat.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDTO {

    @Schema(description = "User's nickname", example = "alice")
    @NotBlank(message = "Nickname is required")
    private String nickname;

    @Schema(description = "User's password", example = "password")
    @NotBlank(message = "Password is required")
    private String password;
}