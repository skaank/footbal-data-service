package com.project.footballApp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.footballApp.singletion.ObjectMapperSingleton;
import com.project.footballApp.client.FootballApiClient;
import com.project.footballApp.config.ExternalFootballServiceConfiguration;
import com.project.footballApp.enums.FootballApiAction;
import com.project.footballApp.exceptionHandling.custom.DefaultError;
import com.project.footballApp.exceptionHandling.custom.FootballServiceAuthenticationFailedException;
import com.project.footballApp.exceptionHandling.custom.NoDataFoundException;
import com.project.footballApp.exceptionHandling.feign.FootballApiErrorResponse;
import com.project.footballApp.model.Team;
import com.project.footballApp.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.footballApp.constants.AppConstants.FOOTBALL_SERVICE_AUTHENTICATION_FAILED_MSG;
import static com.project.footballApp.constants.AppConstants.NO_TEAM_FOUND_MSG;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {


    private ExternalFootballServiceConfiguration footballServiceConfig;
    private FootballApiClient footballApiClient;

    @Autowired
    public TeamServiceImpl(ExternalFootballServiceConfiguration footballServiceConfig, FootballApiClient footballApiClient) {
        this.footballServiceConfig = footballServiceConfig;
        this.footballApiClient = footballApiClient;
    }

    /**
     * this function calls the 3rd party api service to get the list of all teams playing in a league
     * and  after getting the response check for its correctness
     * and then accordingly reply the caller with data or exception.
     *
     * @param leagueId
     * @return list of Teams
     */
    @Override
    public List<Team> getAllTeamsByLeagueId(String leagueId) {
        String allTeams = footballApiClient.getAllTeamsByLeagueId(FootballApiAction.GET_TEAMS.getActionValue(),leagueId,
                footballServiceConfig.getApiKey());

        ObjectMapper mapper = ObjectMapperSingleton.getInstance();
        try {
            return (List<Team>) mapper.readValue(String.valueOf(allTeams), new TypeReference<List<Team>>() {
            });
        } catch (Exception ex) {
            try {
                FootballApiErrorResponse errorResponse = mapper.readValue(String.valueOf(allTeams), new TypeReference<FootballApiErrorResponse>() {
                });
                switch (errorResponse.getMessage()) {
                    case FOOTBALL_SERVICE_AUTHENTICATION_FAILED_MSG ->
                            throw new FootballServiceAuthenticationFailedException(errorResponse.getMessage());
                    case NO_TEAM_FOUND_MSG -> throw new NoDataFoundException(errorResponse.getMessage());
                    default -> throw new DefaultError(errorResponse.getMessage());
                }
            } catch (JsonProcessingException jex) {
                log.error("Exception occurred in processing response for getting all countries api : ", jex);
                throw new DefaultError("Something went wrong");
            }
        }

    }
}
