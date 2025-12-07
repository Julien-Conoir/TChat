package com.tchat.channel.controller;

import com.tchat.channel.dto.MessageChannelRequestDTO;
import com.tchat.channel.dto.MessageChannelResponseDTO;
import com.tchat.channel.service.MessageChannelService;
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
@RequestMapping("/channels/messages")
@RequiredArgsConstructor
public class MessageChannelController {

    private final MessageChannelService messageChannelService;

    @Operation(summary = "Get all messages from a channel", description = "Retrieves all messages from the specified channel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved messages from the channel", content = @Content(schema = @Schema(implementation = MessageChannelResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Channel not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{channelName}")
    public List<MessageChannelResponseDTO> getAllMessagesFromAChannel(
            @RequestHeader("X-User-Id") String userNickname,
            @PathVariable String channelName
    ) {
        return messageChannelService.getAllMessagesFromAChannel(channelName, userNickname);
    }

    @Operation(summary = "Send a message to a channel", description = "Sends a message to the specified channel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Message sent successfully to the channel"),
            @ApiResponse(responseCode = "404", description = "Channel not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping()
    public void sendMessageToAChannel(
            @RequestHeader("X-User-Id") String userNickname,
            @Valid @RequestBody MessageChannelRequestDTO messageChannelRequestDTO
    ) {
        messageChannelService.sendMessageToAChannel(messageChannelRequestDTO, userNickname);
    }
}