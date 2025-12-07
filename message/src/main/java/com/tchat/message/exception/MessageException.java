package com.tchat.message.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MessageException extends RuntimeException {

    private final HttpStatus status;

    public MessageException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public MessageException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }
}
