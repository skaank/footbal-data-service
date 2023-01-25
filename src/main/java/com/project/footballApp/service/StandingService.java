package com.project.footballApp.service;


import com.project.footballApp.model.Standing;

import java.util.List;

public interface StandingService {

    Standing getStandingByCountryLeagueTeamNames(String countryName, String leagueName, String teamName);
    List<Standing> getStandingByLeagueId(String leagueId);
}
