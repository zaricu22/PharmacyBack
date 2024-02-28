package com.example.pharmacyback.exceptions;

import com.example.pharmacyback.exceptions.errors.DateExpiredException;
import com.example.pharmacyback.exceptions.errors.ProductExistsException;
import com.example.pharmacyback.exceptions.errors.ProductNotFoundException;
import com.example.pharmacyback.exceptions.errors.WrongManudacturerException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class, Throwable.class})
    public ResponseEntity<Object> internalExceptionHandler(Exception ex){
        System.out.println(ex);
        ex.printStackTrace();
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.DEFAULT_INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({HandlerMethodValidationException.class, MethodArgumentNotValidException.class})
    public ResponseEntity<Object> methodValidationExceptionExceptionHandler(Exception ex){
        String excpMessage = ex.getMessage();
        if (ex instanceof MethodArgumentNotValidException) {
            String[] messSplited =  excpMessage.split(";");
            String wrongProperty = messSplited[messSplited.length-2]
                    .substring(
                            messSplited[messSplited.length-2].indexOf("[")+1,
                            messSplited[messSplited.length-2].indexOf("]")
                    );
            String cause = messSplited[messSplited.length-1]
                    .substring(
                            messSplited[messSplited.length-1].indexOf("[")+1,
                            messSplited[messSplited.length-1].indexOf("]")
                    );
            excpMessage = wrongProperty+": "+cause;
        }
        System.out.println(ex);
        ex.printStackTrace();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, excpMessage);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> databaseAccessExceptionExceptionHandler(Exception ex){
        System.out.println(ex);
        ex.printStackTrace();
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, ErrorMessages.DATABASE_ACCESS_EXCEPTION);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Object> productNotFoundExceptionHandler(Exception ex){
        System.out.println(ex);
        ex.printStackTrace();
        return buildErrorResponse(HttpStatus.NOT_FOUND, ErrorMessages.PRODUCT_NOT_FOUND_EXCEPTION);
    }

    @ExceptionHandler(ProductExistsException.class)
    public ResponseEntity<Object> productExistsExceptionHandler(Exception ex){
        System.out.println(ex);
        ex.printStackTrace();
        return buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.PRODUCT_EXISTS_EXCEPTION);
    }

    @ExceptionHandler(DateExpiredException.class)
    public ResponseEntity<Object> dateExpiredExceptionHandler(Exception ex){
        System.out.println(ex);
        ex.printStackTrace();
        return buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.DATE_EXPIRED_EXCEPTION);
    }

    @ExceptionHandler(WrongManudacturerException.class)
    public ResponseEntity<Object> wrongManufacturerExceptionHandler(Exception ex){
        System.out.println(ex);
        ex.printStackTrace();
        return buildErrorResponse(HttpStatus.NOT_ACCEPTABLE, ErrorMessages.WRONG_MANUFACTURER_EXCEPTION);
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new ExceptionResponse(message,status));
    }
}
