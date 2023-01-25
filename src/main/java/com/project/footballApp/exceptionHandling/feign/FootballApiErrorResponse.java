package com.project.footballApp.exceptionHandling.feign;

import lombok.Data;

@Data
public class FootballApiErrorResponse {
    private String error;
    private String message;
}
