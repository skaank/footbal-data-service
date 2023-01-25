package com.project.footballApp.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("external-services.football")
public class ExternalFootballServiceConfiguration extends ExternalApiBaseProperties {

    public String getApiKey(){
        return super.getApiKey();
    }

    public String getBaseUrl() {
        return super.getBaseUrl();
    }
}
