package com.tchat.auth.mapper;

import com.tchat.auth.dto.AuthResponseDTO;
import com.tchat.auth.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "authToken", target = "token")
    AuthResponseDTO toAuthResponse(UserEntity user);
}