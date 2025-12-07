package com.tchat.channel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ChannelDTO {

    @Schema(description = "Channel name", example = "general")
    @NotBlank(message = "Channel name is required")
    private String channelName;

    @Schema(description = "List of channel members' nicknames", example = "[\"alice\", \"bob\"]")
    private List<String> members;
}