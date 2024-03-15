package com.pharmacy.application.exceptions.errors;

public class DateExpiredException extends RuntimeException {
    public DateExpiredException(String message) {
        super(message);
    }
}
