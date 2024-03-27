package org.garden.com.exceptions;


public class InvalidCartItemException extends RuntimeException{
    public InvalidCartItemException(String message){
        super(message);
    }
}
