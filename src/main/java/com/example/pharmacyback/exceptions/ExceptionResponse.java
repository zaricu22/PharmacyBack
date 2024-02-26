package com.example.pharmacyback.exceptions;

import org.springframework.http.HttpStatus;

public record ExceptionResponse(
        String message,
        HttpStatus status
) {
}
