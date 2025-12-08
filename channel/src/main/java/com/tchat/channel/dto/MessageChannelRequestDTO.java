package com.tchat.channel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageChannelRequestDTO {

    @Schema(description = "Channel name", example = "general")
    @NotBlank(message = "Channel name is required")
    private String channelName;

    @Schema(description = "Message content", example = "Hello, everyone!")
    @NotBlank(message = "Message content is required")
    private String content;
}