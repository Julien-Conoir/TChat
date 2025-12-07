package com.tchat.auth.service;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class KafkaTopicService {

    private final KafkaAdmin kafkaAdmin;

    private static final String TOPIC_PREFIX = "user-";
    private static final int NUM_PARTITIONS = 1;
    private static final short REPLICATION_FACTOR = 1;

    public void createTopicForUser(String nickname) {
        String topicName = TOPIC_PREFIX + nickname;

        try (AdminClient adminClient = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {

            boolean topicExists = adminClient.listTopics().names().get().contains(topicName);

            if (!topicExists) {
                NewTopic newTopic = new NewTopic(topicName, NUM_PARTITIONS, REPLICATION_FACTOR);
                adminClient.createTopics(Collections.singleton(newTopic)).all().get();
            }

        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
        }
    }
}