package com.tchat.channel.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "messages_channels")
public class MessageChannelEntity {

    @Id
    private String id;

    private String channelName;
    private String senderNickname;
    private String content;

    public MessageChannelEntity(String channelName, String senderNickname, String content) {
        this.channelName = channelName;
        this.senderNickname = senderNickname;
        this.content = content;
    }
}