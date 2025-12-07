package com.tchat.message.controller;

import com.tchat.message.dto.MessageRequestDTO;
import com.tchat.message.dto.MessageResponseDTO;
import com.tchat.message.service.MessageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    @GetMapping
    public List<MessageResponseDTO> getAllPrivateMessages(@RequestHeader("X-User-Id") String userNickname) {
        return messageService.getAllMessages(userNickname);
    }

    @PostMapping
    public void sendPrivateMessage(
            @RequestHeader("X-User-Id") String userNickname,
            @Valid @RequestBody MessageRequestDTO messageRequestDTO
    ) {
        messageService.sendMessage(messageRequestDTO, userNickname);
    }
}