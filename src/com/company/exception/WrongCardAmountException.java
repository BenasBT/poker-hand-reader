package com.company.exception;

public class WrongCardAmountException extends Throwable {
    public WrongCardAmountException(String errorMessage) {
        super(errorMessage);
    }
}
