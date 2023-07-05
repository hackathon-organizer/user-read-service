package com.hackathonorganizer.userreadservice.exception;

import org.springframework.http.HttpStatus;

public class ScheduleException extends BaseException {

    public ScheduleException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
