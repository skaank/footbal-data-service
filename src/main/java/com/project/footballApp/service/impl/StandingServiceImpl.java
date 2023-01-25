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
import com.project.footballApp.model.Standing;
import com.project.footballApp.service.CompetitionService;
import com.project.footballApp.service.CountryService;
import com.project.footballApp.service.StandingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.footballApp.constants.AppConstants.FOOTBALL_SERVICE_AUTHENTICATION_FAILED_MSG;
import static com.project.footballApp.constants.AppConstants.NO_STANDING_FOUND_MSG;

@Service
@Slf4j
public class StandingServiceImpl implements StandingService {

    private ExternalFootballServiceConfiguration footballServiceConfig;
    private FootballApiClient footballApiClient;

    private CompetitionService competitionService;
    private CountryService countryService;

    @Autowired
    public StandingServiceImpl(ExternalFootballServiceConfiguration footballServiceConfig, FootballApiClient footballApiClient
            , CompetitionService competitionService, CountryService countryService) {
        this.footballServiceConfig = footballServiceConfig;
        this.footballApiClient = footballApiClient;
        this.competitionService = competitionService;
        this.countryService = countryService;
    }


    @Override
    public Standing getStandingByCountryLeagueTeamNames(String countryName, String leagueName, String teamName) {

        String countryId = countryService.getCountryIdByCountryName(countryName);
        String leagueId = competitionService.getLeagueIdByLeagueNameAndCountryId(leagueName, countryId);
        Optional<Standing> standingOptional = getStandingByLeagueId(leagueId)
                .stream()
                .filter(standing -> standing.getTeamName().equals(teamName) && standing.getCountryName().equals(countryName))
                .findFirst();

        if (standingOptional.isPresent())
            return standingOptional.get();
        throw new NoDataFoundException("Standing with country : " + countryName + " team : " + teamName + " league : " + leagueName + " not found!");
    }

    @Override
    public List<Standing> getStandingByLeagueId(String leagueId) {
        String allStandings = footballApiClient.getStandingByLeagueId(FootballApiAction.GET_STANDINGS.getActionValue(),
                footballServiceConfig.getApiKey(), leagueId);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(String.valueOf(allStandings), new TypeReference<List<Standing>>() {
            });
        } catch (Exception ex) {
            try {
                FootballApiErrorResponse errorResponse = mapper.readValue(String.valueOf(allStandings), new TypeReference<FootballApiErrorResponse>() {
                });
                switch (errorResponse.getMessage()) {
                    case FOOTBALL_SERVICE_AUTHENTICATION_FAILED_MSG ->
                            throw new FootballServiceAuthenticationFailedException(errorResponse.getMessage());
                    case NO_STANDING_FOUND_MSG ->
                            throw new NoDataFoundException("Standing with league with id : " + leagueId + " not found!");
                    default -> throw new DefaultError(errorResponse.getMessage());
                }
            } catch (JsonProcessingException jex) {
                log.error("Exception occurred in processing response for getting all countries api : {}", jex);
                throw new DefaultError("Something went wrong");
            }
        }
    }
}
