package com.tchat.channel.service;

import com.tchat.channel.dto.ChannelDTO;

import java.util.List;

public interface ChannelService {

    List<ChannelDTO> getAllChannels();
    ChannelDTO createChannel(ChannelDTO channelDTO, String creatorNickname);
    void deleteChannel(String channelName, String userNickname);
    void subscribe(String channelName, String userNickname);
    void unsubscribe(String channelName, String userNickname);
}