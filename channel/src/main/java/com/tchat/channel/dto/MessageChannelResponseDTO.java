package com.tchat.channel.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageChannelResponseDTO {

    @Schema(description = "Channel name", example = "general")
    private String channelName;

    @Schema(description = "Sender nickname", example = "alice")
    private String senderNickname;

    @Schema(description = "Receiver nickname", example = "bob")
    private String receiverNickname;

    @Schema(description = "Message content", example = "Hello, everyone!")
    private String content;
}