package com.teide.tfg.order_service.exception;

public class OrderNotFoundByMailException extends RuntimeException {
    public OrderNotFoundByMailException(String message) {
        super(message);
    }
}
