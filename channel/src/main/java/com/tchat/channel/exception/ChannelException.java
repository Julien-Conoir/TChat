package com.tchat.channel.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ChannelException extends RuntimeException {

    private final HttpStatus status;

    public ChannelException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public ChannelException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
