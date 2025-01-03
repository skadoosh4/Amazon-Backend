package com.example.demo.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class ProfanityFilterException extends CustomBaseException{
    private static final Logger logger = LoggerFactory.getLogger(ProfanityFilterException.class);
    public ProfanityFilterException() {
        super(HttpStatus.NOT_FOUND, new SimpleResponse(ErrorMessage.PROFANITY_FILTER_DOWN.getMessage()));
        logger.error("Profanity Filter Down Exception Thrown " + getClass().getSimpleName());
    }
}
