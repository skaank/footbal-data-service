package com.project.footballApp.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.project.footballApp.model.Competitions;
import com.project.footballApp.service.CompetitionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CompetitionControllerTest {

    @MockBean
    private CompetitionService competitionService;
    @Autowired
    private MockMvc mockMvc;

    List<Competitions> competitions;
    @BeforeEach
    void setUp() {
        String expectedCompetitionResponse = """
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
        try {
           competitions  = new ObjectMapper().readValue(expectedCompetitionResponse, new TypeReference<List<Competitions>>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getAllCompetitionsByCountryId() throws Exception {


        doReturn(competitions).when(competitionService).getAllCompetitionsByCountryId(any());

        mockMvc.perform(get("/competitions/{countryId}",6))
                .andExpect(status().isForbidden());

    }
}