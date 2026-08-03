package com.wipro.exception;


public class InvalidCredentialsException
        extends RuntimeException {

    public InvalidCredentialsException(String message) {
        super(message);
    }
}