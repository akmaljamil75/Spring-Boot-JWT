package com.spring_jwt.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class SpringJwtException extends Exception {
    
    HttpStatus httpStatus;

    public SpringJwtException(String message)
    {
        super(message);
    }

}
