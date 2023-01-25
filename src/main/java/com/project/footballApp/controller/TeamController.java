package com.project.footballApp.controller;

import com.project.footballApp.model.Team;
import com.project.footballApp.model.response.DataResponse;
import com.project.footballApp.service.TeamService;
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
    @GetMapping("/{leagueId}")
    @PreAuthorize("hasAuthority('ROLE_VIEW_TEAM')")
    public ResponseEntity<DataResponse> getAllCompetitionsByCountryId(@PathVariable("leagueId") String leagueId) {
        List<Team> teams = service.getAllTeamsByLeagueId(leagueId);
        DataResponse dataResponse =new DataResponse(true,teams,null);
        return ResponseEntity.ok().body(dataResponse);
    }

}
