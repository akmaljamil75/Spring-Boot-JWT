package com.spring_jwt.exception.impl;

import org.springframework.http.HttpStatus;

import com.spring_jwt.exception.SpringJwtException;

import lombok.Getter;

@Getter
public class BadRequestException extends SpringJwtException {
    
    private HttpStatus httpStatus;

    public BadRequestException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
