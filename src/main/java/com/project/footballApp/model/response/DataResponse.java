package com.project.footballApp.model.response;

import com.project.footballApp.exceptionHandling.apiError.ApiError;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DataResponse {

    private Boolean success;
    private Object data;
    private ApiError error;
}
