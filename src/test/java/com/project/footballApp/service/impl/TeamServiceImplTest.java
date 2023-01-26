package com.project.footballApp.service.impl;

import com.project.footballApp.client.FootballApiClient;
import com.project.footballApp.exceptionHandling.custom.DefaultError;
import com.project.footballApp.exceptionHandling.custom.FootballServiceAuthenticationFailedException;
import com.project.footballApp.exceptionHandling.custom.NoDataFoundException;
import com.project.footballApp.model.Team;
import com.project.footballApp.service.TeamService;
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
class TeamServiceImplTest {

    @Autowired
    private TeamService teamService;
    @MockBean
    private FootballApiClient footballApiClient;


    private String expectedTeamResponse;

    @BeforeEach
    void setUp() {

        expectedTeamResponse = """
                [
                  {
                    "team_key": "73",
                    "team_name": "Atletico Madrid",
                    "team_badge": "https://apiv3.apifootball.com/badges/73_atl.-madrid.jpg",
                    "players": [
                      {
                        "player_key": "106805300",
                        "player_id": "106805300",
                        "player_image": "https://apiv3.apifootball.com/badges/players/31641_i-grbi.jpg",
                        "player_name": "I. Grbić",
                        "player_number": "1",
                        "player_country": "",
                        "player_type": "Goalkeepers",
                        "player_age": "25",
                        "player_match_played": "0",
                        "player_goals": "0",
                        "player_yellow_cards": "0",
                        "player_red_cards": "0",
                        "player_injured": "No",
                        "player_substitute_out": "0",
                        "player_substitutes_on_bench": "32",
                        "player_assists": "0",
                        "player_is_captain": "0",
                        "player_shots_total": "",
                        "player_goals_conceded": "0",
                        "player_fouls_committed": "",
                        "player_tackles": "",
                        "player_blocks": "",
                        "player_crosses_total": "",
                        "player_interceptions": "",
                        "player_clearances": "",
                        "player_dispossesed": "",
                        "player_saves": "",
                        "player_inside_box_saves": "",
                        "player_duels_total": "",
                        "player_duels_won": "",
                        "player_dribble_attempts": "",
                        "player_dribble_succ": "",
                        "player_pen_comm": "",
                        "player_pen_won": "",
                        "player_pen_scored": "0",
                        "player_pen_missed": "0",
                        "player_passes": "",
                        "player_passes_accuracy": "",
                        "player_key_passes": "",
                        "player_woordworks": "",
                        "player_rating": ""
                      }],
                    "coaches": [
                      {
                        "coach_name": "D. Simeone",
                        "coach_country": "",
                        "coach_age": ""
                      }
                    ]
                  }
                ]""";
    }

    @Test
    void getAllTeamsByLeagueId() {

        doReturn(expectedTeamResponse).when(footballApiClient).getAllTeamsByLeagueId(any(),any(),any());

        // Execute the service call
        List<Team> teams = teamService.getAllTeamsByLeagueId("");

        // Assert the response
        Assertions.assertEquals(teams.size(), 1);
        Assertions.assertEquals(teams.get(0).getName(), "Atletico Madrid");
    }


    @Test
    void getAllTeamsByLeagueIdAuthenticationFailedResponse() {

        expectedTeamResponse = "{\"error\":404,\"message\":\"Authentification failed!\"}";
        doReturn(expectedTeamResponse).when(footballApiClient).getAllTeamsByLeagueId(any(),any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(FootballServiceAuthenticationFailedException.class,() ->  teamService.getAllTeamsByLeagueId(""));

    }

    @Test
    void getAllTeamsByLeagueIdIdUnExpectedResponse() {

        expectedTeamResponse = "{\"error\":404,\"message\":\"some random failed!\"}";
        doReturn(expectedTeamResponse).when(footballApiClient).getAllTeamsByLeagueId(any(),any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(DefaultError.class,() ->  teamService.getAllTeamsByLeagueId(""));
    }

    @Test
    void ggetAllTeamsByLeagueIdNoLeaguePresentResponse() {
        expectedTeamResponse = "{\"error\":404,\"message\":\"No teams could be found!\"}";
        doReturn(expectedTeamResponse).when(footballApiClient).getAllTeamsByLeagueId(any(),any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(NoDataFoundException.class,() ->  teamService.getAllTeamsByLeagueId("7"));
    }

}