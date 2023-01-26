package com.project.footballApp.controller;

import com.project.footballApp.model.Competitions;
import com.project.footballApp.model.response.DataResponse;
import com.project.footballApp.service.CompetitionService;
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

/**
 * This controller handles endpoints related to competitions.leagues info
 */
@RestController
@RequestMapping("/competitions")
public class CompetitionController {

    private CompetitionService service;

    @Autowired
    public CompetitionController(CompetitionService service) {
        this.service = service;
    }

    @Operation(summary = "Get All Competitions/Leagues for a country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Info for all Leagues taking place in the respective country",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No leagues for the respective country",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden (only open for users with view competition access)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))})
    })
    @GetMapping("/{countryId}")
    @PreAuthorize("hasAuthority('ROLE_VIEW_COMPETITION')")
    public ResponseEntity<DataResponse> getAllCompetitionsByCountryId(@PathVariable("countryId") String countryId) {
        List<Competitions> countryCompetitions = service.getAllCompetitionsByCountryId(countryId);
        DataResponse dataResponse = new DataResponse(true, countryCompetitions, null);
        return ResponseEntity.ok().body(dataResponse);
    }
}
