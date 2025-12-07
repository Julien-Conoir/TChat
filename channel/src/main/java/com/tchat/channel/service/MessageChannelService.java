package com.tchat.channel.service;

import com.tchat.channel.dto.MessageChannelRequestDTO;
import com.tchat.channel.dto.MessageChannelResponseDTO;

import java.util.List;

public interface MessageChannelService {

    List<MessageChannelResponseDTO> getAllMessagesFromAChannel(String channelName, String userNickname);
    void sendMessageToAChannel(MessageChannelRequestDTO messageChannelRequestDTO, String userNickname);
}