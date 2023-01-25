package com.project.footballApp.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Team {

    @JsonProperty("team_key")
    private String key;
    @JsonProperty("team_name")
    private String name;
    @JsonProperty("team_badge")
    private String badge;

//    @JsonProperty("players")
//    private List<Player> players;
//
//    @JsonProperty("coaches")
//    private List<Coach> coaches;
}
