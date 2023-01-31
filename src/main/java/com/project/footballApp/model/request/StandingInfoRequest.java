package com.project.footballApp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StandingInfoRequest {

    @JsonProperty("countryName")
    @Length(max = 100,min = 1)
    @NotEmpty
    private String countryName;
    @JsonProperty("leagueName")
    @Length(max = 100,min = 1)
    @NotEmpty
    private String leagueName;
    @JsonProperty("teamName")
    @Length(max = 100,min = 1)
    @NotEmpty
    private String teamName;
}
