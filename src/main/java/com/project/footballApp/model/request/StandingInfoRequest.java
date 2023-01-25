package com.project.footballApp.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class StandingInfoRequest {

    @JsonProperty("countryName")
    @Length(max = 100,min = 1)
    private String countryName;
    @JsonProperty("leagueName")
    @Length(max = 100,min = 1)
    private String leagueName;
    @JsonProperty("teamName")
    @Length(max = 100,min = 1)
    private String teamName;
}
