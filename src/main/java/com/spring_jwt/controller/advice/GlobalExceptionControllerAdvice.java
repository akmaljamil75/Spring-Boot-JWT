package com.spring_jwt.controller.advice;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.mongodb.MongoException;
import com.mongodb.MongoWriteException;
import com.spring_jwt.exception.SpringJwtException;
import com.spring_jwt.utility.ResponseUtils;

@ControllerAdvice
public class GlobalExceptionControllerAdvice {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) 
    {
        Map<String, Object> maps = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName;
            try {
                fieldName = ((FieldError) error).getField();
            } catch (ClassCastException ex) {
                fieldName = error.getObjectName();
            }
            String message = error.getDefaultMessage();
            maps.put(fieldName, message);
        });
        e.printStackTrace();
        return ResponseUtils.failed("Bad Request", maps, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SpringJwtException.class)
    public Object handleApiException(SpringJwtException e)
    {   
        e.printStackTrace();
        return ResponseUtils.failed(e.getMessage(), e.getHttpStatus());
    }

    @ExceptionHandler(MongoException.class)
    public Object handleMongoException(Exception e)
    {   
        e.printStackTrace();
        return ResponseUtils.failed(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MongoWriteException.class)
    public Object handleMongoWriteException(MongoWriteException e)
    {   
        e.printStackTrace();
        return ResponseUtils.failed(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public Object handleUsernameNotFoundException(UsernameNotFoundException e)
    {   
        e.printStackTrace();
        return ResponseUtils.failed(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MailException.class)
    public Object handleException(MailException e)
    {   
        e.printStackTrace();
        Object err = null;;
        if(e instanceof MailSendException) {
            err = ResponseUtils.failed("Invalid Address Mail", HttpStatus.BAD_REQUEST);
        }
        else if(e instanceof MailAuthenticationException) {
            err = ResponseUtils.failed("Invalid Authenticated Mail", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else { 
            err = ResponseUtils.failed(e.getCause().getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return err;
    }

    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e)
    {   
        e.printStackTrace();
        return ResponseUtils.failed(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
