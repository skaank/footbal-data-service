package com.project.footballApp.exceptionHandling.custom;

public class FootballServiceAuthenticationFailedException extends RuntimeException {

    public FootballServiceAuthenticationFailedException(String message) {
        super(message);
    }

}
