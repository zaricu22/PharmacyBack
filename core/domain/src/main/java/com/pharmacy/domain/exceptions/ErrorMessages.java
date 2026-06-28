package com.pharmacy.domain.exceptions;

public class ErrorMessages {

    public static final String DEFAULT_INTERNAL_SERVER_ERROR =
            "An error occurred while processing request!";

    public static final String DATABASE_ACCESS_EXCEPTION =
            "An error occurred while accessing database!";

    public static final String PRODUCT_NOT_FOUND_EXCEPTION = "No product(s) found in database!";

    public static final String PRODUCT_EXISTS_EXCEPTION = "Product already exists in database!";

    public static final String WRONG_MANUFACTURER_EXCEPTION = "Selected manufacturer doesn't exist!";

    public static final String EXPIRED_DATE_EXCEPTION = "Product's date is expired!";

    public static final String NEGATIVE_PRICE_EXCEPTION = "Product's price cannot be negative!";

    public static final String EMPTY_NAME_EXCEPTION = "Product's name cannot be empty!";

    public static final String BAD_ID_EXCEPTION = "Entity id must be set!";
}
