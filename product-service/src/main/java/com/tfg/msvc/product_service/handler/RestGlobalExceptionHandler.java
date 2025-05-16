package com.tfg.msvc.product_service.handler;

import com.tfg.msvc.product_service.exception.ProductNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestGlobalExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Void> handleProductNotFoundException(ProductNotFoundException e) {
        log.error(e.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Void> handleException(Exception e, HttpServletRequest request) {
        log.error("Fatal error, {},{},{}", e.getMessage(), e.getClass(), request.getRequestURI());
        return ResponseEntity.internalServerError().build();
    }
}
