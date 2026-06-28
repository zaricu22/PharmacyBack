package com.pharmacy.domain.exceptions;

public class ProductDateExpiredException extends RuntimeException {
    public ProductDateExpiredException(String message) {
        super(message);
    }
}
