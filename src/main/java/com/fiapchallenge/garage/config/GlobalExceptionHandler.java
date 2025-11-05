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

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SoatValidationException.class)
    public ResponseEntity<Map<String, String>> handleSoatValidationException(SoatValidationException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, String>> handleInsufficientStockException(InsufficientStockException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, String>> handleConstraintViolationException(ConstraintViolationException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(ReportErrorException.class)
    public ResponseEntity<Map<String, String>> handleReportErrorException(ReportErrorException ex) {
        return ResponseEntity.internalServerError().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(SoatNotFoundException.class)
    public ResponseEntity<String> handleSoatNotFoundException(SoatNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleException(Exception ex) {
        return ResponseEntity.internalServerError().body(Map.of("error", "Ocorreu um erro inesperado"));
    }
}
