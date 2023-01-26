# football-data-service

This is a private service which provides data related to football.

Current goal of this service is to provide standing of a football team playing in a particular league in a particular country.

The service can be easily extended to get other information as well.

Currently service use 3rd party integration with football apis to provide the above information.
The apis used from 3rd party include:

1. Get all countries information where football is played
2. Get all competitions information  
3. Get all teams information
4. Get information regrading standing of a team playing in a league in a country.


Flow to get information regarding standing of a comapny is:

1. Get registered with the service ( eg get respective roles like view_country,view_team,etc)

 ![](E:\UserController_addUser.svg)  ![](E:\UserServiceImpl_addUser.svg)

2. Get auth token

![](E:\AuthController_authenticateAndGetToken.svg)



3. Call the standing api with the auth token.


![](E:\StandingController_getStanding.svg)   ![](E:\StandingServiceImpl_getStandingByCountryLeagueTeamNames.svg)


