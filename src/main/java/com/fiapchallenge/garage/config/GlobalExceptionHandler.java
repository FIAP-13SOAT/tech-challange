package com.fiapchallenge.garage.config;

import com.fiapchallenge.garage.shared.exception.SoatValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SoatValidationException.class)
    public ResponseEntity<String> handleSoatValidationException(SoatValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
