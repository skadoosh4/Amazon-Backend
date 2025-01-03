package com.example.demo.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
    PRODUCT_NOT_FOUND("Product not found"),
    PRODUCT_NAME_CANNOT_BE_EMPTY("Product name cannot be empty"),
    PRODUCT_PRICE_CANNOT_BE_NEGATIVE("Product Price cannot be negative"),
    PRODUCT_CATEGORY_IS_NOT_AVAILABLE("Product category is not available"),
    PRODUCT_REGION_IS_NOT_AVAILABLE("Product region is not available"),
    PRODUCT_HAS_PROFANITY("Product cannot be saved due to explicit keywords"),
    PROFANITY_FILTER_DOWN("Profanity Filter external service is down");

    private final String message;

}
