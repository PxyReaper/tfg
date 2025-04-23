package com.teide.tfg.order_service.exception;

public class OrderNotFoundByIdException extends RuntimeException {
    public OrderNotFoundByIdException(String message) {
        super(message);
    }
}
