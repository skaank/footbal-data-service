package com.project.footballApp.exceptionHandling.custom;

public class NoDataFoundException extends RuntimeException {

    public NoDataFoundException(String message) {
        super(message);
    }

}
