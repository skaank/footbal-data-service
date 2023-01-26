package com.project.footballApp.controller;

import com.project.footballApp.model.Standing;
import com.project.footballApp.model.request.StandingInfoRequest;
import com.project.footballApp.model.response.DataResponse;
import com.project.footballApp.service.StandingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Get Standing of a team playing in a league in a country")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Info of performance pof a team",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Malformed request",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "404", description = "No team cound be found for respective league and country",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "403", description = "Forbidden (only open for users with view standings access)",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))})
    })
    @PostMapping()
    @PreAuthorize("hasAuthority('ROLE_VIEW_STANDING')")
    public ResponseEntity<DataResponse> getStanding(@RequestBody @Valid StandingInfoRequest request) {
        Standing standing = service.getStandingByCountryLeagueTeamNames(request.getCountryName(),request.getLeagueName(),request.getTeamName());
        DataResponse dataResponse =new DataResponse(true,standing,null);
        return ResponseEntity.ok().body(dataResponse);
    }

}
