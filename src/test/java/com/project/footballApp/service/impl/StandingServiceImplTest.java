package com.project.footballApp.service.impl;

import com.project.footballApp.client.FootballApiClient;
import com.project.footballApp.exceptionHandling.custom.DefaultError;
import com.project.footballApp.exceptionHandling.custom.FootballServiceAuthenticationFailedException;
import com.project.footballApp.exceptionHandling.custom.NoDataFoundException;
import com.project.footballApp.model.Standing;
import com.project.footballApp.service.CompetitionService;
import com.project.footballApp.service.CountryService;
import com.project.footballApp.service.StandingService;
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
class StandingServiceImplTest {

    @Autowired
    private StandingService standingService;
    @MockBean
    private FootballApiClient footballApiClient;
    @MockBean
    private CountryService countryService;
    @MockBean
    private CompetitionService competitionService;

    private String expectedStandingResponse;
    @BeforeEach
    void setUp() {
        expectedStandingResponse = """
                [
                    {
                        "country_name": "Spain",
                        "league_id": "302",
                        "league_name": "La Liga",
                        "team_id": "76",
                        "team_name": "Real Madrid",
                        "overall_promotion": "Promotion - Champions League (Group Stage)",
                        "overall_league_position": "1",
                        "overall_league_payed": "37",
                        "overall_league_W": "24",
                        "overall_league_D": "9",
                        "overall_league_L": "4",
                        "overall_league_GF": "65",
                        "overall_league_GA": "27",
                        "overall_league_PTS": "81",
                        "home_league_position": "",
                        "home_promotion": "",
                        "home_league_payed": "",
                        "home_league_W": "",
                        "home_league_D": "",
                        "home_league_L": "",
                        "home_league_GF": "",
                        "home_league_GA": "",
                        "home_league_PTS": "",
                        "away_league_position": "",
                        "away_promotion": "",
                        "away_league_payed": "",
                        "away_league_W": "",
                        "away_league_D": "",
                        "away_league_L": "",
                        "away_league_GF": "",
                        "away_league_GA": "",
                        "away_league_PTS": "",
                        "league_round": "Current",
                        "team_badge": "https://apiv3.apifootball.com/badges/76_real-madrid.jpg",
                        "fk_stage_key": "402",
                        "stage_name": "Current"
                    },
                    {
                        "country_name": "Spain",
                        "league_id": "302",
                        "league_name": "La Liga",
                        "team_id": "73",
                        "team_name": "Atl. Madrid",
                        "overall_promotion": "Promotion - Champions League (Group Stage)",
                        "overall_league_position": "2",
                        "overall_league_payed": "36",
                        "overall_league_W": "24",
                        "overall_league_D": "8",
                        "overall_league_L": "4",
                        "overall_league_GF": "63",
                        "overall_league_GA": "23",
                        "overall_league_PTS": "80",
                        "home_league_position": "",
                        "home_promotion": "",
                        "home_league_payed": "",
                        "home_league_W": "",
                        "home_league_D": "",
                        "home_league_L": "",
                        "home_league_GF": "",
                        "home_league_GA": "",
                        "home_league_PTS": "",
                        "away_league_position": "",
                        "away_promotion": "",
                        "away_league_payed": "",
                        "away_league_W": "",
                        "away_league_D": "",
                        "away_league_L": "",
                        "away_league_GF": "",
                        "away_league_GA": "",
                        "away_league_PTS": "",
                        "league_round": "Current",
                        "team_badge": "https://apiv3.apifootball.com/badges/73_atletico-madrid.jpg",
                        "fk_stage_key": "402",
                        "stage_name": "Current"
                    }]""";
    }

    @Test
    void getStandingByCountryLeagueTeamNames() {
        doReturn(expectedStandingResponse).when(footballApiClient).getStandingByLeagueId(any(),any(),any());
        doReturn("dumbValue").when(countryService).getCountryIdByCountryName(any());
        doReturn("dumbValue").when(competitionService).getLeagueIdByLeagueNameAndCountryId(any(),any());

        // Execute the service call
        Standing standings = standingService.getStandingByCountryLeagueTeamNames("Spain","La Liga","Real Madrid");

        // Assert the response
        Assertions.assertNotNull(standings);
        Assertions.assertEquals(standings.getTeamId(), "76");
        Assertions.assertThrows(NoDataFoundException.class,() ->  standingService.getStandingByCountryLeagueTeamNames("Spain","La Liga","Real M"));
    }

    @Test
    void getStandingByLeagueIdByCountryIdAuthenticationFailedResponse() {

        expectedStandingResponse = "{\"error\":404,\"message\":\"Authentification failed!\"}";
        doReturn(expectedStandingResponse).when(footballApiClient).getStandingByLeagueId(any(),any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(FootballServiceAuthenticationFailedException.class,() ->  standingService.getStandingByLeagueId(""));

    }

    @Test
    void getStandingByLeagueIdByCountryIdUnExpectedResponse() {

        expectedStandingResponse = "{\"error\":404,\"message\":\"some random failed!\"}";
        doReturn(expectedStandingResponse).when(footballApiClient).getStandingByLeagueId(any(),any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(DefaultError.class,() ->  standingService.getStandingByLeagueId(""));
    }

    @Test
    void getStandingByLeagueIdNoLeaguePresentResponse() {
        expectedStandingResponse = "{\"error\":404,\"message\":\"Standing not found!\"}";
        doReturn(expectedStandingResponse).when(footballApiClient).getStandingByLeagueId(any(),any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(NoDataFoundException.class,() ->  standingService.getStandingByLeagueId(""));
    }

    @Test
    void getStandingByLeagueId() {
        doReturn(expectedStandingResponse).when(footballApiClient).getStandingByLeagueId(any(),any(),any());

        // Execute the service call
        List<Standing> standings = standingService.getStandingByLeagueId("");

        // Assert the response
        Assertions.assertEquals(standings.size(), 2);
        Assertions.assertEquals(standings.get(0).getTeamName(), "Real Madrid");
    }
}