package com.commerce.ecommerce.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
@NoArgsConstructor
@Getter
public class CustomException extends RuntimeException{
    protected String message;
    protected HttpStatus status;


    public CustomException(String message) {
        super(message);
        this.message = message;
    }

    public CustomException(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}

