package com.project.footballApp.service;

import com.project.footballApp.model.Team;

import java.util.List;

public interface TeamService {

    List<Team> getAllTeamsByLeagueId(String leagueId);
}
