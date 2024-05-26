package com.craft.exception;

public class DataViolationsFoundException extends RuntimeException{
    public DataViolationsFoundException(String message) {
        super(message);
    }
}
