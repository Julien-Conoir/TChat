package com.tchat.message.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "messages")
public class MessageEntity {

    @Id
    private String id;

    private String senderNickname;
    private String receiverNickname;
    private String content;

    public MessageEntity(String senderNickname, String receiverNickname, String content) {
        this.senderNickname = senderNickname;
        this.receiverNickname = receiverNickname;
        this.content = content;
    }
}