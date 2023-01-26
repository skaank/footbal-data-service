package com.project.footballApp.constants;

/**
 * This class contains all the app related constants
 */
public class AppConstants {

    public static final String FOOTBALL_SERVICE_AUTHENTICATION_FAILED_MSG = "Authentication failed!";
    public static final String NO_LEAGUE_FOUND_MSG = "No league found (please check your plan)!!";
    public static final String NO_TEAM_FOUND_MSG = "No teams could be found!";
    public static final String NO_STANDING_FOUND_MSG = "Standing not found!";

    public static final String TOKEN_AUTHORIZATION_KEY = "Authorization";
    public static final String BEARER_WITH_SPACE_KEY = "Bearer ";

    public static final String[] PUBLIC_URLS = {
            "/v3/api-docs",
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

}
