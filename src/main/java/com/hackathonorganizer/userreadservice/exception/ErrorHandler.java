package com.hackathonorganizer.userreadservice.exception;

import com.hackathonorganizer.userreadservice.user.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
class ErrorHandler {

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ErrorResponse handleUserNotFoundException(UserNotFoundException exception){
        return new ErrorResponse(HttpStatus.NOT_FOUND.toString(),
                exception.getMessage(), LocalDateTime.now());
    }
}
