package com.fiapchallenge.garage.config;

import com.fiapchallenge.garage.shared.exception.InsufficientStockException;
import com.fiapchallenge.garage.shared.exception.ResourceNotFoundException;
import com.fiapchallenge.garage.shared.exception.ReportErrorException;
import com.fiapchallenge.garage.shared.exception.SoatNotFoundException;
import com.fiapchallenge.garage.shared.exception.SoatValidationException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SoatValidationException.class)
    public ResponseEntity<String> handleSoatValidationException(SoatValidationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<String> handleInsufficientStockException(InsufficientStockException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
    @ExceptionHandler(ReportErrorException.class)
    public ResponseEntity<String> handleReportErrorException(ReportErrorException ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
    @ExceptionHandler(SoatNotFoundException.class)
    public ResponseEntity<String> handleSoatNotFoundException(SoatNotFoundException ex) {
        return ResponseEntity.notFound().build();

    }

}
