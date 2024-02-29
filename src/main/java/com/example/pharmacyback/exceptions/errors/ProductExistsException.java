package com.example.pharmacyback.exceptions.errors;

public class ProductExistsException extends RuntimeException {
  public ProductExistsException(String message) {
    super(message);
  }
}
