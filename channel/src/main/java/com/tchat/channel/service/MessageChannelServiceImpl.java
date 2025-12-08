package com.tchat.channel.service;

import com.tchat.channel.dto.MessageChannelRequestDTO;
import com.tchat.channel.dto.MessageChannelResponseDTO;
import com.tchat.channel.entity.MessageChannelEntity;
import com.tchat.channel.mapper.MessageChannelMapper;
import com.tchat.channel.repository.MessageChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageChannelServiceImpl implements MessageChannelService {

    private final MessageChannelRepository messageChannelRepository;
    private final MessageChannelMapper messageChannelMapper;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public List<MessageChannelResponseDTO> getAllMessagesFromAChannel(String channelName, String userNickname) {
        return messageChannelRepository.findAllByChannelNameAndSenderNickname(channelName, userNickname)
                .stream()
                .map(messageChannelMapper::toMessageChannelResponseDTO)
                .toList();
    }

    @Override
    public void sendMessageToAChannel(MessageChannelRequestDTO messageChannelRequestDTO, String userNickname) {
        kafkaProducerService.sendGroupMessage(
                userNickname,
                messageChannelRequestDTO.getChannelName(),
                messageChannelRequestDTO.getContent()
        );

        MessageChannelEntity messageChannelEntity = new MessageChannelEntity(
                messageChannelRequestDTO.getChannelName(),
                userNickname,
                messageChannelRequestDTO.getContent()
        );

        messageChannelRepository.save(messageChannelEntity);
    }
}