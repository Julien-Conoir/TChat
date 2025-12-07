package com.tchat.channel.controller;

import com.tchat.channel.dto.ChannelDTO;
import com.tchat.channel.service.ChannelService;
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
@RequestMapping("/channels")
@RequiredArgsConstructor
public class ChannelController {

    private final ChannelService channelService;

    @Operation(summary = "Retrieve all channels", description = "Fetches a list of all available channels.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved list of channels", content = @Content(schema = @Schema(implementation = ChannelDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public List<ChannelDTO> getAllChannels() {
        return channelService.getAllChannels();
    }

    @Operation(summary = "Create a new channel", description = "Creates a new channel with the provided details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Channel created successfully", content = @Content(schema = @Schema(implementation = ChannelDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ChannelDTO createChannel(
            @RequestHeader("X-User-Id") String userNickname,
            @Valid @RequestBody ChannelDTO channelDTO
    ) {
        return channelService.createChannel(channelDTO, userNickname);
    }

    @Operation(summary = "Delete a channel", description = "Deletes the specified channel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Channel deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Channel not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{channelName}")
    public void deleteChannel(
            @RequestHeader("X-User-Id") String userNickname,
            @PathVariable("channelName") String channelName
    ) {
        channelService.deleteChannel(channelName, userNickname);
    }

    @Operation(summary = "Subscribe to a channel", description = "Subscribes the user to the specified channel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Subscribed to channel successfully"),
            @ApiResponse(responseCode = "404", description = "Channel not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/subscribe/{channelName}")
    public void subscribe(
            @RequestHeader("X-User-Id") String userNickname,
            @PathVariable("channelName") String channelName
    ) {
        channelService.subscribe(channelName, userNickname);
    }

    @Operation(summary = "Unsubscribe from a channel", description = "Unsubscribes the user from the specified channel.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Unsubscribed from channel successfully"),
            @ApiResponse(responseCode = "404", description = "Channel not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/unsubscribe/{channelName}")
    public void unsubscribe(
            @RequestHeader("X-User-Id") String userNickname,
            @PathVariable("channelName") String channelName) {
        channelService.unsubscribe(channelName, userNickname);
    }
}