package com.tchat.channel.mapper;

import com.tchat.channel.dto.ChannelDTO;
import com.tchat.channel.entity.ChannelEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ChannelMapper {

    ChannelDTO toChannelDTO(ChannelEntity channelEntity);
}