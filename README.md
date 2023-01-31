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


**Pre-requisites to run this code :**

1. Mysql installed in your system.
2. Database with name footballproject created(if not created please create one).
3. Add a user with ROLE_ADMIN.

**SetUp steps (for running locally):**


1. Clone the repository from github.
2. Switch to master branch.
3. Open swagger on http://localhost:8080/swagger-ui/index.html
4. From there get token for admin user.
5. With admin user token add another user using register user wpi with roles ROLE_VIEW_COUNTRY,ROLE_VIEW_TEAM,ROLE_VIEW_COMPETITION,ROLE_VIEW_STANDING
6. Now fetch token for the above added user using authenticate api.
7. Use this token to access all other pai in the swagger.

