package com.example.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class ProductNotFoundException extends CustomBaseException{
    private final Logger logger = LoggerFactory.getLogger(ProductNotFoundException.class);
    public ProductNotFoundException() {
        super(HttpStatus.NOT_FOUND, new SimpleResponse(ErrorMessage.PRODUCT_NOT_FOUND.getMessage()));
        logger.error("Product not found exception thrown " +getClass().getSimpleName());
    }
}
