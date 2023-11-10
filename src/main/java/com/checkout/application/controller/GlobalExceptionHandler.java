package com.checkout.application.controller;

import com.checkout.application.dto.ApiResponse;
import com.checkout.domain.exception.BusinessException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    ResponseEntity<ApiResponse> handleBusinessException(BusinessException exception) {
        String exceptionMessage = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(false, exceptionMessage);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleBusinessException(MethodArgumentNotValidException exception) {
        String exceptionMessage = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .reduce((message1, message2) -> message1 + " | " + message2)
                .orElse("Validation failed.");
        ApiResponse apiResponse = new ApiResponse(false, exceptionMessage);
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    ResponseEntity<ApiResponse> handleGenericException(Exception exception) {
        String exceptionMessage = exception.getMessage();
        ApiResponse apiResponse = new ApiResponse(false, exceptionMessage);
        return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }



}
