package com.hackathonorganizer.userreadservice.exception;

import java.util.List;

public record ErrorResponse (
    String message,
    List<String> details
) {
}
