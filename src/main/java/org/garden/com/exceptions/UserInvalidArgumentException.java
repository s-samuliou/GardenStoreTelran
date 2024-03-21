package org.garden.com.exceptions;

public class UserInvalidArgumentException extends RuntimeException{

    public UserInvalidArgumentException(String message) {
        super(message);
    }
}
