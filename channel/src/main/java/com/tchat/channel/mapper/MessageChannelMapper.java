package com.tchat.channel.mapper;

import com.tchat.channel.dto.MessageChannelResponseDTO;
import com.tchat.channel.entity.MessageChannelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MessageChannelMapper {

    MessageChannelResponseDTO toMessageChannelResponseDTO(MessageChannelEntity messageChannelEntity);
}