package com.project.footballApp.controller;

import com.project.footballApp.model.Competitions;
import com.project.footballApp.model.response.DataResponse;
import com.project.footballApp.service.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/competitions")
public class CompetitionController {

    private CompetitionService service;

    @Autowired
    public CompetitionController(CompetitionService service) {
        this.service = service;
    }

    @GetMapping("/{countryId}")
    @PreAuthorize("hasAuthority('ROLE_VIEW_COMPETITION')")
    public ResponseEntity<DataResponse> getAllCompetitionsByCountryId(@PathVariable("countryId") String countryId) {
        List<Competitions> countryCompetitions = service.getAllCompetitionsByCountryId(countryId);
        DataResponse dataResponse =new DataResponse(true,countryCompetitions,null);
        return ResponseEntity.ok().body(dataResponse);
    }
}
