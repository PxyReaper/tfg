package com.teide.tfg.order_service.handlers;

import com.teide.tfg.order_service.exception.OrderNotFoundByIdException;
import com.teide.tfg.order_service.exception.OrderNotFoundByMailException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(OrderNotFoundByIdException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Void> handleOrderNotFoundException(OrderNotFoundByIdException ex){
        log.error(ex.getMessage());
        return ResponseEntity.notFound().build();

    }
    @ExceptionHandler(OrderNotFoundByMailException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Void> handleORderNotFoundByMailException(OrderNotFoundByMailException ex){
        log.error(ex.getMessage());
        return ResponseEntity.notFound().build();

    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public  ResponseEntity<Void> handleException(Exception ex, HttpServletRequest request){
        log.error("Fatal error, {},{},{}",ex.getClass(),ex.getMessage(),request.getRequestURI());
        return ResponseEntity.internalServerError().build();
    }

}
