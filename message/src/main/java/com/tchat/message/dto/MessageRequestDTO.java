package com.tchat.message.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageRequestDTO {

    @Schema(description = "Receiver nickname", example = "bob")
    @NotBlank(message = "Receiver nickname is required")
    private String receiverNickname;

    @Schema(description = "Message content", example = "Hello, Bob!")
    @NotBlank(message = "Message content is required")
    private String content;
}
