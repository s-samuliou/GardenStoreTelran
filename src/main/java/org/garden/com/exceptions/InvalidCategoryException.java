package org.garden.com.exceptions;

public class InvalidCategoryException extends RuntimeException {

    public InvalidCategoryException(String message){
        super(message);
    }
}
