package com.caremind.test.exception;

public class DuplicatedIdException extends RuntimeException {

    public DuplicatedIdException(String message) {
        super(message);
    }
}
