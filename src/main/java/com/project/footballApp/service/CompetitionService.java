package com.project.footballApp.service;

import com.project.footballApp.model.Competitions;

import java.util.List;

public interface CompetitionService {

    List<Competitions> getAllCompetitionsByCountryId(String countryId);

    String getLeagueIdByLeagueNameAndCountryId(String leagueName, String countryId);

}
