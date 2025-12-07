package com.tchat.message.service;

import com.tchat.message.dto.KafkaMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerService {

    private final KafkaTemplate<String, KafkaMessageDTO> kafkaTemplate;

    private static final String TOPIC_PREFIX = "user-";

    public void sendPrivateMessage(String senderNickname, String receiverNickname, String content) {
        KafkaMessageDTO message = new KafkaMessageDTO(
                senderNickname,
                receiverNickname,
                content,
                KafkaMessageDTO.MessageType.PRIVATE,
                null
        );

        String topic = TOPIC_PREFIX + receiverNickname;
        kafkaTemplate.send(topic, message);
    }
}