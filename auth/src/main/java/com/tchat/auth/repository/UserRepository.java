package com.tchat.auth.repository;

import com.tchat.auth.entity.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserEntity, String> {

    Optional<UserEntity> findByNickname(String nickname);
    Optional<UserEntity> findByAuthToken(String authToken);
}