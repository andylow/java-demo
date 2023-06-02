package com.example.demo.exception;

/**
 * @author [yun]
 */
public class BadRequestException extends RuntimeException {

    public BadRequestException(final String errorMessage) {
        super(errorMessage);
    }
}
