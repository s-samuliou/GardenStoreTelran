package org.garden.com.exceptions;

public class OrderInvalidArgumentException extends RuntimeException {

    public OrderInvalidArgumentException(String message) {
        super(message);
    }
}
