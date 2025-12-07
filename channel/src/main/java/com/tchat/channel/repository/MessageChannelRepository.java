package com.tchat.channel.repository;

import com.tchat.channel.entity.MessageChannelEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageChannelRepository extends MongoRepository<MessageChannelEntity, String> {

    List<MessageChannelEntity> findAllByChannelNameAndSenderNickname(String channelName, String userNickname);
}