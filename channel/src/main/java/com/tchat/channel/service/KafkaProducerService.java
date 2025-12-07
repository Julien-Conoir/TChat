package com.tchat.channel.service;

import com.tchat.channel.dto.KafkaMessageDTO;
import com.tchat.channel.entity.ChannelEntity;
import com.tchat.channel.exception.ChannelException;
import com.tchat.channel.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, KafkaMessageDTO> kafkaTemplate;
    private final ChannelRepository channelRepository;

    private static final String TOPIC_PREFIX = "user-";

    public void sendGroupMessage(String senderNickname, String channelName, String content) {
        ChannelEntity channel = channelRepository.findByChannelName(channelName)
                .orElseThrow(() -> new ChannelException("Channel not found", HttpStatus.NOT_FOUND));

        for (String member : channel.getMembers()) {
            KafkaMessageDTO message = new KafkaMessageDTO(
                    senderNickname,
                    member,
                    content,
                    KafkaMessageDTO.MessageType.GROUP,
                    channelName
            );

            String topic = TOPIC_PREFIX + member;
            kafkaTemplate.send(topic, message);
        }
    }
}