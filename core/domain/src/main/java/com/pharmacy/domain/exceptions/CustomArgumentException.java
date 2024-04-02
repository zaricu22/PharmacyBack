package com.pharmacy.domain.exceptions;

public class CustomArgumentException extends RuntimeException {
    public CustomArgumentException(String message) {
        super(message);
    }
}
