package com.pharmacy.domain.exceptions;

public class WrongManufacturerException extends RuntimeException {
    public WrongManufacturerException(String message) {
        super(message);
    }
}
