package org.garden.com.exceptions;


public class InvalidCategoryArgumentException extends RuntimeException {
    public InvalidCategoryArgumentException(String message){
        super(message);
    }
}
