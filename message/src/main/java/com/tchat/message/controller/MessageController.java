package com.tchat.message.controller;

import com.tchat.message.dto.MessageRequestDTO;
import com.tchat.message.dto.MessageResponseDTO;
import com.tchat.message.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Get all private messages", description = "Retrieves all private messages sent by the authenticated user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved messages", content = @Content(schema = @Schema(implementation = MessageResponseDTO.class))),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<MessageResponseDTO> getAllPrivateMessages(@RequestHeader("X-User-Id") String userNickname) {
        return messageService.getAllMessages(userNickname);
    }

    @Operation(summary = "Send a private message", description = "Sends a private message from the authenticated user to another user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sent successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid message data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized access"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public void sendPrivateMessage(
            @RequestHeader("X-User-Id") String userNickname,
            @Valid @RequestBody MessageRequestDTO messageRequestDTO
    ) {
        messageService.sendMessage(messageRequestDTO, userNickname);
    }
}