package org.garden.com.exceptions;

public class OrderItemNotFoundException extends RuntimeException{

    public OrderItemNotFoundException(String message) {
        super(message);
    }
}