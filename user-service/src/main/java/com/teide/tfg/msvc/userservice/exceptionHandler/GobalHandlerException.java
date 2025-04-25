package com.teide.tfg.msvc.userservice.exceptionHandler;

import com.teide.tfg.msvc.userservice.exception.UserNameNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GobalHandlerException {
    @ExceptionHandler(UserNameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Void> handleUsernameNotFoundException(UserNameNotFoundException ex ){
        log.error(ex.getMessage(),ex.getClass());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Void> handleException(Exception ex, HttpServletRequest request){
        log.error("Fatal error, {},{},{}", ex.getClass(),ex.getMessage(),request.getRequestURI());
        return ResponseEntity.internalServerError().build();
    }
}
