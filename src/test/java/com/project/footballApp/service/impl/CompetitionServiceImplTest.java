package com.project.footballApp.service.impl;

import com.project.footballApp.client.FootballApiClient;
import com.project.footballApp.exceptionHandling.custom.DefaultError;
import com.project.footballApp.exceptionHandling.custom.FootballServiceAuthenticationFailedException;
import com.project.footballApp.exceptionHandling.custom.NoDataFoundException;
import com.project.footballApp.model.Competitions;
import com.project.footballApp.service.CompetitionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class CompetitionServiceImplTest {

    @Autowired
    private CompetitionService competitionService;

    @MockBean
    private FootballApiClient footballApiClient;


    private String expectedCompetitionResponse;

    @BeforeEach
    public void setUp() {
        expectedCompetitionResponse = """
                [
                    {
                        "country_id": "6",
                        "country_name": "Spain",
                        "league_id": "300",
                        "league_name": "Copa del Rey",
                        "league_season": "2020/2021",
                        "league_logo": "https://apiv3.apifootball.com/badges/logo_leagues/300_copa-del-rey.png",
                        "country_logo": "https://apiv3.apifootball.com/badges/logo_country/6_spain.png"
                    },
                    {
                        "country_id": "6",
                        "country_name": "Spain",
                        "league_id": "302",
                        "league_name": "La Liga",
                        "league_season": "2020/2021",
                        "league_logo": "https://apiv3.apifootball.com/badges/logo_leagues/302_la-liga.png",
                        "country_logo": "https://apiv3.apifootball.com/badges/logo_country/6_spain.png"
                    }]""";

    }
    @Test
    void getAllCompetitionsByCountryId() {


        doReturn(expectedCompetitionResponse).when(footballApiClient).getAllCompetitions(any(),any(),any());

        // Execute the service call
        List<Competitions> competitions = competitionService.getAllCompetitionsByCountryId("6");

        // Assert the response
        Assertions.assertEquals(competitions.size(), 2);
        Assertions.assertEquals(competitions.get(0).getLeagueId(), "300");

    }


    @Test
    void getLeagueIdByLeagueNameAndCountryId() {
        doReturn(expectedCompetitionResponse).when(footballApiClient).getAllCompetitions(any(),any(),any());

        // Execute the service call
        String leagueId = competitionService.getLeagueIdByLeagueNameAndCountryId("La Liga","6");

        // Assert the response
        Assertions.assertNotNull(leagueId);
        Assertions.assertEquals(leagueId, "302");
    }

    @Test
    void getLeagueIdByLeagueNameAndCountryIdWhenLeagueNotPresent() {
        doReturn(expectedCompetitionResponse).when(footballApiClient).getAllCompetitions(any(),any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(NoDataFoundException.class,() ->  competitionService.getLeagueIdByLeagueNameAndCountryId("Pa Liga","6"));
    }

    @Test
    void getAllCompetitionsByCountryIdAuthenticationFailedResponse() {

        expectedCompetitionResponse = "{\"error\":404,\"message\":\"Authentification failed!\"}";
        doReturn(expectedCompetitionResponse).when(footballApiClient).getAllCompetitions(any(),any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(FootballServiceAuthenticationFailedException.class,() ->  competitionService.getAllCompetitionsByCountryId("6"));

    }

    @Test
    void getAllCompetitionsByCountryIdUnExpectedResponse() {

        expectedCompetitionResponse = "{\"error\":404,\"message\":\"some random failed!\"}";
        doReturn(expectedCompetitionResponse).when(footballApiClient).getAllCompetitions(any(),any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(DefaultError.class,() ->  competitionService.getAllCompetitionsByCountryId("7"));
    }

    @Test
    void getAllCompetitionsByCountryIdNoLeaguePresentResponse() {
        expectedCompetitionResponse = "{\"error\":404,\"message\":\"No league found (please check your plan)!!\"}";
        doReturn(expectedCompetitionResponse).when(footballApiClient).getAllCompetitions(any(),any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(NoDataFoundException.class,() ->  competitionService.getAllCompetitionsByCountryId("7"));
    }

}