package com.company.exception;

public class NoPlayersException extends RuntimeException {
    public NoPlayersException(String errorMessage) {
        super(errorMessage);
    }
}
