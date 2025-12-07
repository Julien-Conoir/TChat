package com.tchat.message.service;

import com.tchat.message.dto.MessageRequestDTO;
import com.tchat.message.dto.MessageResponseDTO;
import com.tchat.message.entity.MessageEntity;
import com.tchat.message.mapper.MessageMapper;
import com.tchat.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements  MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;

    @Override
    public List<MessageResponseDTO> getAllMessages(String userNickname) {
        return messageRepository.findAllBySenderNickname(userNickname)
                .stream()
                .map(messageMapper::toMessageResponseDTO)
                .toList();
    }

    @Override
    public void sendMessage(MessageRequestDTO messageRequestDTO, String userNickname) {
        //TODO implement kafak producer to send private message to user topic

        MessageEntity messageEntity = new MessageEntity(
                userNickname,
                messageRequestDTO.getReceiverNickname(),
                messageRequestDTO.getContent()
        );

        messageRepository.save(messageEntity);
    }
}