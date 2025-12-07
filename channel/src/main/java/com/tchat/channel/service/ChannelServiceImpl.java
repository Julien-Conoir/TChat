package com.tchat.channel.service;

import com.tchat.channel.dto.ChannelDTO;
import com.tchat.channel.entity.ChannelEntity;
import com.tchat.channel.exception.ChannelException;
import com.tchat.channel.mapper.ChannelMapper;
import com.tchat.channel.repository.ChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChannelServiceImpl implements ChannelService {

    private final ChannelMapper channelMapper;
    private final ChannelRepository channelRepository;

    @Override
    public List<ChannelDTO> getAllChannels() {
        return channelRepository.findAll().stream().map(channelMapper::toChannelDTO).toList();
    }

    @Override
    public ChannelDTO createChannel(ChannelDTO channelDTO, String creatorNickname) {
        if (channelRepository.findByChannelName(channelDTO.getChannelName()).isPresent()) {
            throw new ChannelException("Channel name already exists", HttpStatus.BAD_REQUEST);
        }

        ChannelEntity channelEntity = new ChannelEntity(
                channelDTO.getChannelName(),
                creatorNickname,
                List.of(creatorNickname)
        );

        return channelMapper.toChannelDTO(channelRepository.save(channelEntity));
    }

    @Override
    public void deleteChannel(String channelName, String userNickname) {

        ChannelEntity channel = channelRepository.findByChannelName(channelName)
                .orElseThrow(() -> new ChannelException("Channel does not exist", HttpStatus.NOT_FOUND));

        if (!channel.getCreatorNickname().equals(userNickname)) {
            throw new ChannelException("Only the creator can delete the channel", HttpStatus.FORBIDDEN);
        }

        channelRepository.deleteById(channel.getId());
    }

    @Override
    public void subscribe(String channelName, String userNickname) {

        ChannelEntity channel = channelRepository.findByChannelName(channelName)
                .orElseThrow(() -> new ChannelException("Channel does not exist", HttpStatus.NOT_FOUND));

        if(channel.getMembers().contains(userNickname)) {
            throw new ChannelException("User is already subscribed to the channel", HttpStatus.BAD_REQUEST);
        }

        channel.getMembers().add(userNickname);
        channelRepository.save(channel);
    }

    @Override
    public void unsubscribe(String channelName, String userNickname) {

        ChannelEntity channel = channelRepository.findByChannelName(channelName)
                .orElseThrow(() -> new ChannelException("Channel does not exist", HttpStatus.NOT_FOUND));

        if(!channel.getMembers().contains(userNickname)) {
            throw new ChannelException("User is not subscribed to the channel", HttpStatus.BAD_REQUEST);
        }

        channel.getMembers().remove(userNickname);
        channelRepository.save(channel);
    }
}