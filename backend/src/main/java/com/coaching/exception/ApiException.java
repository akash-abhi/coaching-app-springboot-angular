package com.coaching.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiException extends RuntimeException {
    private String message;
    private int statusCode;

    public ApiException(String message) {
        super(message);
        this.message = message;
        this.statusCode = 500;
    }
}
