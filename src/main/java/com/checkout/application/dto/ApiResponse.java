package com.checkout.application.dto;

import lombok.Data;

@Data
public class ApiResponse {

    private boolean result;
    private String message;

    public ApiResponse(boolean result, String message) {
        this.result = result;
        this.message = message;
    }
}
