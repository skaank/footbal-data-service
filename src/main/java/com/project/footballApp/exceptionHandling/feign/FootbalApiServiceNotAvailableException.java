package com.project.footballApp.exceptionHandling.feign;

public class FootbalApiServiceNotAvailableException extends RuntimeException {

    public FootbalApiServiceNotAvailableException(String message) {
        super(message);
    }
}
