package com.project.footballApp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Player extends Person{

    @JsonProperty("player_key")
    private String playerKey;
    @JsonProperty("player_id")
    private String playerId;
    @JsonProperty("player_image")
    private String image;
    @JsonProperty("player_name")
    private String name;
    @JsonProperty("player_number")
    private String number;
    @JsonProperty("player_country")
    private String country;

    @JsonProperty("player_type")
    private String type;

    @JsonProperty("player_age")
    private String age;

    @JsonProperty("player_match_played")
    private String matchPlayed;

    @JsonProperty("player_goals")
    private String goals;
    @JsonProperty("player_yellow_cards")
    private String yellowCards;
    @JsonProperty("player_red_cards")
    private String redCards;
    @JsonProperty("player_injured")
    private String injured;

    @JsonProperty("player_substitute_out")
    private String substitute_out;
    @JsonProperty("player_substitutes_on_bench")
    private String substitutes_on_bench;
    @JsonProperty("player_assists")
    private String assists;

//    @JsonProperty("player_birthdate")
//    private String birthDate;
    @JsonProperty("player_is_captain")
    private String is_captain;

    @JsonProperty("player_shots_total")
    private String shotsTotal;
    @JsonProperty("player_goals_conceded")
    private String goalsConceded;
    @JsonProperty("player_fouls_committed")
    private String foulsCommitted;
    @JsonProperty("player_tackles")
    private String tackles;
    @JsonProperty("player_blocks")
    private String blocks;

    @JsonProperty("player_crosses_total")
    private String crossesTotal;
    @JsonProperty("player_interceptions")
    private String interceptions;
    @JsonProperty("player_clearances")
    private String clearances;
    @JsonProperty("player_dispossesed")
    private String dispossesed;
    @JsonProperty("player_saves")
    private String saves;

    @JsonProperty("player_inside_box_saves")
    private String insideBoxSaves;
    @JsonProperty("player_duels_total")
    private String duelsTotal;
    @JsonProperty("player_duels_won")
    private String duelsWon;
    @JsonProperty("player_dribble_attempts")
    private String dribbleAttempts;
    @JsonProperty("player_dribble_succ")
    private String dribbleSucc;

    @JsonProperty("player_pen_comm")
    private String penComm;
    @JsonProperty("player_pen_won")
    private String penWon;
    @JsonProperty("player_pen_scored")
    private String penScored;
    @JsonProperty("player_pen_missed")
    private String penMissed;
    @JsonProperty("player_passes")
    private String passes;

    @JsonProperty("player_passes_accuracy")
    private String passesAccuracy;
    @JsonProperty("player_key_passes")
    private String keyPasses;
    @JsonProperty("player_woordworks")
    private String woordWorks;
    @JsonProperty("player_rating")
    private String rating;

}
