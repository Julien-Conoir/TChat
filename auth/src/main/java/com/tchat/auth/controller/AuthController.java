package com.tchat.auth.controller;

import com.tchat.auth.dto.AuthRequestDTO;
import com.tchat.auth.dto.AuthResponseDTO;
import com.tchat.auth.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "Register a new user", description = "Creates a new user account with the provided nickname and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Nickname already taken"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/register")
    public AuthResponseDTO register(@Valid @RequestBody AuthRequestDTO request) {
        return authService.register(request);
    }

    @Operation(summary = "User login", description = "Authenticates a user with the provided nickname and password.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully", content =  @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Invalid password"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/login")
    public AuthResponseDTO login(@Valid @RequestBody AuthRequestDTO request) {
        return authService.login(request);
    }

    @Operation(summary = "Validate authentication token", description = "Validates the provided authentication token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Token is valid", content = @Content(schema = @Schema(implementation = AuthResponseDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Token is invalid or expired"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/token")
    public AuthResponseDTO validateToken(@RequestHeader("Authorization") String token) {
        return authService.isTokenValid(token);
    }
}