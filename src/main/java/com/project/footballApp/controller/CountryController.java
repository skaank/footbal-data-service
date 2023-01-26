package com.project.footballApp.controller;

import com.project.footballApp.model.CountryInfo;
import com.project.footballApp.model.response.DataResponse;
import com.project.footballApp.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/country")
public class CountryController {
    private CountryService service;
    @Autowired
    public CountryController(CountryService service) {
        this.service = service;
    }

    @Operation(summary = "Get info for all countries")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Info for all countries",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))}),
            @ApiResponse(responseCode = "404", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = DataResponse.class))})
    })
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ROLE_VIEW_COUNTRY')")
    public ResponseEntity<DataResponse> getAllCountries() {
        List<CountryInfo> allCountryData = service.getAllCountries();
        DataResponse dataResponse = new DataResponse(true, allCountryData, null);
        return ResponseEntity.ok().body(dataResponse);
    }
}
