package com.company.exception;

public class BadCardSymbolException extends Throwable {
    public BadCardSymbolException(String errorMessage) {
        super(errorMessage);
    }
}
