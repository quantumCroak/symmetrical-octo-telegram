package com.kokodi.cartgame.util;

import com.kokodi.cartgame.util.exception.NotUniqueException;
import com.kokodi.cartgame.util.exception.RegisterUserException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RegisterUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse registerUserExceptionHandler(RegisterUserException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(NotUniqueException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNotUniqueException(NotUniqueException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
