package com.tchat.channel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KafkaMessageDTO {

    private String senderNickname;
    private String receiverNickname;
    private String content;
    private MessageType messageType;
    private String channelName;

    public enum MessageType {
        PRIVATE,
        GROUP
    }
}