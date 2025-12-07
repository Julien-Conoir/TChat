package com.tchat.auth.service;

import com.tchat.auth.dto.AuthRequestDTO;
import com.tchat.auth.dto.AuthResponseDTO;
import com.tchat.auth.entity.UserEntity;
import com.tchat.auth.exception.AuthException;
import com.tchat.auth.mapper.UserMapper;
import com.tchat.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public AuthResponseDTO register(AuthRequestDTO request) {

        if (userRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new AuthException("Nickname already taken", HttpStatus.CONFLICT);
        }

        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());

        UserEntity user = new UserEntity(request.getNickname(), hashedPassword);

        userRepository.save(user);

        return userMapper.toAuthResponse(user);
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {

        UserEntity user = userRepository.findByNickname(request.getNickname())
                .orElseThrow(() -> new AuthException("User not found", HttpStatus.NOT_FOUND));

        if (!BCrypt.checkpw(request.getPassword(), user.getPasswordHash())) {
            throw new AuthException("Invalid password", HttpStatus.UNAUTHORIZED);
        }

        user.setAuthToken(UUID.randomUUID().toString());
        userRepository.save(user);

        return userMapper.toAuthResponse(user);
    }

    @Override
    public AuthResponseDTO isTokenValid(String token) {
        UserEntity user = userRepository.findByAuthToken(token)
                .orElseThrow(() -> new AuthException("Invalid token", HttpStatus.UNAUTHORIZED));

         return userMapper.toAuthResponse(user);
    }
}