package com.project.footballApp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class AuthRequest {

    @JsonProperty("username")
    @NotEmpty
    private String username ;
    @JsonProperty("password")
    @NotEmpty
    private String password;
}
