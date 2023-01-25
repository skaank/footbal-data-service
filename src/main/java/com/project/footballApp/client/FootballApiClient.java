package com.project.footballApp.client;

import com.project.footballApp.exceptionHandling.feign.FootballApiServiceNotAvailableExceptionHandler;
import com.project.footballApp.exceptionHandling.feign.HandleFeignException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "football-api-proxy", url = "${external-services.football.base-url}")
public interface FootballApiClient {

    @GetMapping()
    @HandleFeignException(FootballApiServiceNotAvailableExceptionHandler.class)
    String getAllCountries(@RequestParam(value = "action") String action, @RequestParam(value = "APIkey") String apiKey);


    @GetMapping()
    @HandleFeignException(FootballApiServiceNotAvailableExceptionHandler.class)
    String getAllCompetitions(@RequestParam(value = "action") String action,@RequestParam(value = "country_id") String countryId, @RequestParam(value = "APIkey") String apiKey);

    @GetMapping()
    @HandleFeignException(FootballApiServiceNotAvailableExceptionHandler.class)
    String getAllTeamsByLeagueId(@RequestParam(value = "action") String action,@RequestParam(value = "league_id") String leagueId, @RequestParam(value = "APIkey") String apiKey);

    @GetMapping()
    @HandleFeignException(FootballApiServiceNotAvailableExceptionHandler.class)
    String getStandingByLeagueId(@RequestParam(value = "action") String action, @RequestParam(value = "APIkey") String apiKey, @RequestParam(value = "league_id")  String leagueId);
}
