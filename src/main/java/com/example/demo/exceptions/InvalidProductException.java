package com.example.demo.exceptions;

import com.example.demo.product.model.ProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class InvalidProductException extends CustomBaseException{
    private final Logger logger = LoggerFactory.getLogger(InvalidProductException.class);
    public InvalidProductException(SimpleResponse simpleResponse , ProductRequest request) {
        super(HttpStatus.BAD_REQUEST , simpleResponse);
        logger.error("Product not valid Exception thrown + " + request +  simpleResponse  + " " + getClass().getSimpleName());
    }
}
