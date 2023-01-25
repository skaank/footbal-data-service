package com.project.footballApp.exceptionHandling.feign;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class FootballApiServiceNotAvailableExceptionHandler implements FeignHttpExceptionHandler {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception handle(Response response) {
        if (response.status() == HttpStatus.SERVICE_UNAVAILABLE.value()) {
            try {
                String responseBody = new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8);
                ErrorResponse errorResponse = objectMapper.readValue(responseBody, ErrorResponse.class);
                return new FootbalApiServiceNotAvailableException(errorResponse.getMessage());
            } catch (IOException e) {
                throw new RuntimeException("Error while deserializing the response body");
            }
        }
        return null;
    }
}
