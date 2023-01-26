package com.project.footballApp.client;

import com.project.footballApp.exceptionHandling.feign.FootballApiServiceNotAvailableExceptionHandler;
import com.project.footballApp.exceptionHandling.feign.HandleFeignException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * A declarative client for 3rd part service integration of the Football service
 */
@FeignClient(name = "football-api-proxy", url = "${external-services.football.base-url}")
public interface FootballApiClient {


    /**
     * Returns the list of all countries from 3rd party football service
     * the result is captured into a string because of unstandardized api responses from 3rd party service.
     * This String is then if correctly parsed is converted into @CountryInfo object
     *
     * @param action type of action
     * @param apiKey api key for authentication
     * @return json in a string format
     */
    @GetMapping()
    @HandleFeignException(FootballApiServiceNotAvailableExceptionHandler.class)
    String getAllCountries(@RequestParam(value = "action") String action, @RequestParam(value = "APIkey") String apiKey);


    /**
     * Returns the list of all competitions/leagues taking place in a country from 3rd party football service
     * the result is captured into a string because of unstandardized api responses from 3rd party service.
     * This String is then if correctly parsed is converted into @Competitions object
     *
     * @param action type of action
     * @param countryId county id
     * @param apiKey api kety for authentication
     * @return json in a string format
     */
    @GetMapping()
    @HandleFeignException(FootballApiServiceNotAvailableExceptionHandler.class)
    String getAllCompetitions(@RequestParam(value = "action") String action,@RequestParam(value = "country_id") String countryId, @RequestParam(value = "APIkey") String apiKey);

    /**
     * Returns the list of all teams playing in a league from 3rd party football service
     * the result is captured into a string because of unstandardized api responses from 3rd party service.
     * This String is then if correctly parsed is converted into @Team object
     *
     * @param action the type of action
     * @param leagueId league id
     * @param apiKey for authentication
     * @return All teams info playing in a league
     */
    @GetMapping()
    @HandleFeignException(FootballApiServiceNotAvailableExceptionHandler.class)
    String getAllTeamsByLeagueId(@RequestParam(value = "action") String action,@RequestParam(value = "league_id") String leagueId, @RequestParam(value = "APIkey") String apiKey);


    /**
     * Returns the standing of a team playing in a league in a country from 3rd party football service
     * the result is captured into a string because of unstandardized api responses from 3rd party service.
     * This String is then if correctly parsed is converted into @Team object
     *
     * @param action the type of action
     * @param apiKey for authentication
     * @param leagueId league id
     * @return Standing of a team
     */
    @GetMapping()
    @HandleFeignException(FootballApiServiceNotAvailableExceptionHandler.class)
    String getStandingByLeagueId(@RequestParam(value = "action") String action, @RequestParam(value = "APIkey") String apiKey, @RequestParam(value = "league_id")  String leagueId);
}
