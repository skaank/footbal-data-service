package com.project.footballApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Coach extends Person{
    @JsonProperty("coach_name")
    private String name;

    @JsonProperty("coach_country")
    private String country;

    @JsonProperty("coach_age")
    private Integer age;

}
