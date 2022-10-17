package com.hackathonorganizer.userreadservice.exception;

import java.time.LocalDateTime;

record ErrorResponse(
        String httpStatus,
        String message,
        LocalDateTime localDateTime
) {

}
