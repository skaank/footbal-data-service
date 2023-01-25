package com.project.footballApp.controller;

import com.project.footballApp.model.Standing;
import com.project.footballApp.model.request.StandingInfoRequest;
import com.project.footballApp.model.response.DataResponse;
import com.project.footballApp.service.StandingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/standings")
public class StandingController {

    private StandingService service;

    @Autowired
    public StandingController(StandingService service) {
        this.service = service;
    }
    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_VIEW_STANDING')")
    public ResponseEntity<DataResponse> getStanding(@RequestBody @Valid StandingInfoRequest request) {
        Standing standing = service.getStandingByCountryLeagueTeamNames(request.getCountryName(),request.getLeagueName(),request.getTeamName());
        DataResponse dataResponse =new DataResponse(true,standing,null);
        return ResponseEntity.ok().body(dataResponse);
    }

}
