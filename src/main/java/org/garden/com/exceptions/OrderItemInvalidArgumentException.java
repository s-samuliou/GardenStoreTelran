package org.garden.com.exceptions;

public class OrderItemInvalidArgumentException extends RuntimeException{

    public OrderItemInvalidArgumentException(String message){
        super(message);
    }
}
