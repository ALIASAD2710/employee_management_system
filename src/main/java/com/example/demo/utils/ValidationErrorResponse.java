package com.example.demo.utils;

import java.util.List;

public class ValidationErrorResponse {
    private String message;
    private List<ErrorDetail> details;

    public ValidationErrorResponse(String message, List<ErrorDetail> details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public List<ErrorDetail> getDetails() {
        return details;
    }

}
