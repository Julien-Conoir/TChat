package com.tchat.channel.repository;

import com.tchat.channel.entity.ChannelEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChannelRepository extends MongoRepository<ChannelEntity, String> {

    Optional<ChannelEntity> findByChannelName(String channelName);
}