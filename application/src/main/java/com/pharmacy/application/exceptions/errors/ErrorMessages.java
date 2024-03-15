package com.pharmacy.application.exceptions.errors;

public class ErrorMessages {
    public static final String DEFAULT_INTERNAL_SERVER_ERROR =
            "An error occurred while processing request";

    public static final String DATABASE_ACCESS_EXCEPTION =
            "An error occurred while accessing database";

    public static final String PRODUCT_NOT_FOUND_EXCEPTION = "No product(s) found in database";

    public static final String PRODUCT_EXISTS_EXCEPTION = "Product already exists in database";

    public static final String DATE_EXPIRED_EXCEPTION = "Product date is expired";

    public static final String WRONG_MANUFACTURER_EXCEPTION = "Selected manufacturer doesn't exist";
}
