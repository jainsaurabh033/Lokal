package com.lokal.lokal.exception;

import java.util.Map;

public record ErrorResponse(
    int status,
    String error,
    String message,
    String path,
    Map<String, String> validationErrors
){}
