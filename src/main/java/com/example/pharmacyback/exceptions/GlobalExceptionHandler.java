package com.example.pharmacyback.exceptions;

import com.example.pharmacyback.exceptions.errors.ProductExistsException;
import com.example.pharmacyback.exceptions.errors.ProductNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, Throwable.class})
    public ResponseEntity<Object> internalExceptionHandler(Exception ex){
        System.out.println(ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.DEFAULT_INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> databaseAccessExceptionExceptionHandler(Exception ex){
        System.out.println(ex);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.DATABASE_ACCESS_EXCEPTION);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> productNotFoundExceptionHandler(Exception ex){
        System.out.println(ex);
        return buildErrorResponse(HttpStatus.NOT_FOUND, ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);
    }

    @ExceptionHandler(ProductExistsException.class)
    public ResponseEntity<Object> productExistsExceptionHandler(Exception ex){
        System.out.println(ex);
        return buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.PRODUCT_EXISTS_EXCEPTION);
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ExceptionResponse(message,status));
    }
}
