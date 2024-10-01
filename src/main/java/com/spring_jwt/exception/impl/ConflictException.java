package com.spring_jwt.exception.impl;

import org.springframework.http.HttpStatus;

import com.spring_jwt.exception.SpringJwtException;

import lombok.Getter;

@Getter
public class ConflictException extends SpringJwtException {

    private HttpStatus httpStatus;

    public ConflictException(String message) {
        super(message);
        this.httpStatus = HttpStatus.CONFLICT;
    }
        
}
