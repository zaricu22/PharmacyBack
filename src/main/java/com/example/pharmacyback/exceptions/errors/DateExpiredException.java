package com.example.pharmacyback.exceptions.errors;

public class DateExpiredException extends RuntimeException {
  public DateExpiredException(String message) {
    super(message);
  }
}
