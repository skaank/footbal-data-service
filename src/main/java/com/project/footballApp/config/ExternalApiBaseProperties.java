package com.project.footballApp.config;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ExternalApiBaseProperties {
    private String baseUrl;
    private String apiKey;



}
