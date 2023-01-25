package com.project.footballApp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class AddUserRequest {

    @JsonProperty("name")
    @Length(max = 100,min = 2)
    private String name ;
    @JsonProperty("email")
    @Email
    private String email;

    @JsonProperty("password")
    @Length(max = 100,min = 4)
    private String password;

    @JsonProperty("roles")
    private String roles;
}
