package com.tchat.message.repository;

import com.tchat.message.entity.MessageEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<MessageEntity, String> {

    List<MessageEntity> findAllBySenderNickname(String senderNickname);
}