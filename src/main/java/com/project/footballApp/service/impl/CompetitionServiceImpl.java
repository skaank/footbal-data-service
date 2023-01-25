package com.project.footballApp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.footballApp.client.FootballApiClient;
import com.project.footballApp.config.ExternalFootballServiceConfiguration;
import com.project.footballApp.enums.FootballApiAction;
import com.project.footballApp.exceptionHandling.custom.DefaultError;
import com.project.footballApp.exceptionHandling.custom.FootballServiceAuthenticationFailedException;
import com.project.footballApp.exceptionHandling.custom.NoDataFoundException;
import com.project.footballApp.exceptionHandling.feign.FootballApiErrorResponse;
import com.project.footballApp.model.Competitions;
import com.project.footballApp.service.CompetitionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.footballApp.constants.AppConstants.FOOTBALL_SERVICE_AUTHENTICATION_FAILED_MSG;
import static com.project.footballApp.constants.AppConstants.NO_LEAGUE_FOUND_MSG;

@Service
@Slf4j
public class CompetitionServiceImpl implements CompetitionService {

    private ExternalFootballServiceConfiguration footballServiceConfig;
    private FootballApiClient footballApiClient;


    @Autowired
    public CompetitionServiceImpl(ExternalFootballServiceConfiguration footballServiceConfig, FootballApiClient footballApiClient) {
        this.footballServiceConfig = footballServiceConfig;
        this.footballApiClient = footballApiClient;
    }

    @Override
    public List<Competitions> getAllCompetitionsByCountryId(String countryId) {
        String countryLeagues = footballApiClient.getAllCompetitions(FootballApiAction.GET_LEAGUES.getActionValue(), countryId,
                footballServiceConfig.getApiKey());

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(String.valueOf(countryLeagues), new TypeReference<List<Competitions>>() {
            });
        } catch (Exception ex) {
            try {
                FootballApiErrorResponse errorResponse = mapper.readValue(String.valueOf(countryLeagues), new TypeReference<FootballApiErrorResponse>() {
                });
                switch (errorResponse.getMessage()) {
                    case FOOTBALL_SERVICE_AUTHENTICATION_FAILED_MSG ->
                            throw new FootballServiceAuthenticationFailedException(errorResponse.getMessage());
                    case NO_LEAGUE_FOUND_MSG -> throw new NoDataFoundException(errorResponse.getMessage());
                    default -> throw new DefaultError(errorResponse.getMessage());
                }
            } catch (JsonProcessingException jex) {
                log.error("Exception occurred in processing response for getting leagues for country with id : {} : {}", countryId, jex);
                throw new DefaultError("Something went wrong");
            }
        }
    }

    @Override
    public String getLeagueIdByLeagueNameAndCountryId(String leagueName, String countryId) {
        Optional<Competitions> competitionsOptional = getAllCompetitionsByCountryId(countryId).stream().filter(competitions -> competitions.getLeagueName().equals(leagueName)).findFirst();
        if (competitionsOptional.isPresent())
            return competitionsOptional.get().getLeagueId();
        throw new NoDataFoundException("League with name : "+ leagueName +" not found!");
    }
}
