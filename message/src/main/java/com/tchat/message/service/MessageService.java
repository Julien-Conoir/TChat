package com.tchat.message.service;

import com.tchat.message.dto.MessageRequestDTO;
import com.tchat.message.dto.MessageResponseDTO;

import java.util.List;

public interface MessageService {

    List<MessageResponseDTO> getAllMessages(String userNickname);
    void sendMessage(MessageRequestDTO messageRequestDTO, String userNickname);
}