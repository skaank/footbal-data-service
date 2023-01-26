package com.project.footballApp.controller;

import com.project.footballApp.model.Team;
import com.project.footballApp.model.response.DataResponse;
import com.project.footballApp.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teams")
public class TeamController {

    private TeamService service;


    @Autowired
    public TeamController(TeamService service) {
        this.service = service;
    }

    @Operation(summary = "Get info all teams playing in a league")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Info of teams playing in respective league",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No team could be found for respective league",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden (only open for users with view teams access)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))})
    })
    @GetMapping("/{leagueId}")
    @PreAuthorize("hasAuthority('ROLE_VIEW_TEAM')")
    public ResponseEntity<DataResponse> getAllTeamsByLeagueId(@PathVariable("leagueId") String leagueId) {
        List<Team> teams = service.getAllTeamsByLeagueId(leagueId);
        DataResponse dataResponse =new DataResponse(true,teams,null);
        return ResponseEntity.ok().body(dataResponse);
    }

}
