package com.project.footballApp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum FootballApiAction {

    GET_ALL_COUNTRIES("get_countries"),

    GET_LEAGUES("get_leagues"),

    GET_TEAMS("get_teams"),

    GET_STANDINGS("get_standings");

    private final String actionValue;

}