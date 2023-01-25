package com.project.footballApp.exceptionHandling.feign;

import feign.Response;

public interface FeignHttpExceptionHandler {
    Exception handle(Response response);
}
