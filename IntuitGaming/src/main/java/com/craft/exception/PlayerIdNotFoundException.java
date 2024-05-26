package com.craft.exception;

public class PlayerIdNotFoundException extends RuntimeException{
    public PlayerIdNotFoundException(String message) {
        super(message);
    }
}
