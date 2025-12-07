package com.tchat.channel.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@Document(collection = "channels")
public class ChannelEntity {

    @Id
    private String id;

    private String channelName;
    private String creatorNickname;
    private List<String> members;

    public ChannelEntity(String channelName, String creatorNickname, List<String> members) {
        this.channelName = channelName;
        this.creatorNickname = creatorNickname;
        this.members = members;
    }
}