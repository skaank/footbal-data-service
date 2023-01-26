package com.project.footballApp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {

    @JsonProperty("username")
    @NotEmpty
    private String username ;
    @JsonProperty("password")
    @NotEmpty
    private String password;
}
