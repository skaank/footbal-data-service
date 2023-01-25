package com.project.footballApp.exceptionHandling.custom;

public class AlreadyExistException extends RuntimeException {

    public AlreadyExistException(String message) {
        super(message);
    }

}
