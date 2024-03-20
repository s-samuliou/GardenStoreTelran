package org.garden.com.exceptions;


public class ProductInvalidArgumentException extends RuntimeException {

    public ProductInvalidArgumentException(String message) {
        super(message);
    }
}
