package com.alom.push.service;

import com.alom.push.dto.KafkaMessageDTO;
import com.alom.push.tcp.ClientManager;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

import jakarta.annotation.PreDestroy;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class DynamicKafkaConsumerService {

    private final ConcurrentKafkaListenerContainerFactory<String, KafkaMessageDTO> kafkaListenerContainerFactory;
    private final ClientManager clientManager;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final ConcurrentHashMap<String, ConcurrentMessageListenerContainer<String, KafkaMessageDTO>> containers = new ConcurrentHashMap<>();

    private static final String TOPIC_PREFIX = "user-";
    public void startListeningForUser(String nickname) {
        String topic = TOPIC_PREFIX + nickname;

        if (containers.containsKey(nickname)) {
            return;
        }

        ContainerProperties containerProperties = new ContainerProperties(topic);
        containerProperties.setMessageListener((MessageListener<String, KafkaMessageDTO>) record -> {
            handleMessage(nickname, record);
        });

        ConcurrentMessageListenerContainer<String, KafkaMessageDTO> container =
                new ConcurrentMessageListenerContainer<>(
                        kafkaListenerContainerFactory.getConsumerFactory(),
                        containerProperties
                );

        container.setBeanName("container-" + nickname);
        container.start();

        containers.put(nickname, container);
    }

    public void stopListeningForUser(String nickname) {
        ConcurrentMessageListenerContainer<String, KafkaMessageDTO> container = containers.remove(nickname);

        if (container != null) {
            container.stop();
        }
    }

    private void handleMessage(String nickname, ConsumerRecord<String, KafkaMessageDTO> record) {
        KafkaMessageDTO message = record.value();

        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            clientManager.sendMessageToClient(nickname, jsonMessage);
        } catch (JsonProcessingException e) {
        }
    }

    @PreDestroy
    public void shutdown() {
        containers.values().forEach(ConcurrentMessageListenerContainer::stop);
        containers.clear();
    }
}

