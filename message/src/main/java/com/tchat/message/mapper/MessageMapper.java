package com.tchat.message.mapper;

import com.tchat.message.dto.MessageResponseDTO;
import com.tchat.message.entity.MessageEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageResponseDTO toMessageResponseDTO(MessageEntity messageEntity);
}