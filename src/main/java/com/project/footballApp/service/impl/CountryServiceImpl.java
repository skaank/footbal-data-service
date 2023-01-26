package com.project.footballApp.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.footballApp.client.FootballApiClient;
import com.project.footballApp.config.ExternalFootballServiceConfiguration;
import com.project.footballApp.enums.FootballApiAction;
import com.project.footballApp.exceptionHandling.custom.DefaultError;
import com.project.footballApp.exceptionHandling.custom.FootballServiceAuthenticationFailedException;
import com.project.footballApp.exceptionHandling.custom.NoDataFoundException;
import com.project.footballApp.exceptionHandling.feign.FootballApiErrorResponse;
import com.project.footballApp.model.CountryInfo;
import com.project.footballApp.service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.project.footballApp.constants.AppConstants.FOOTBALL_SERVICE_AUTHENTICATION_FAILED_MSG;

@Service
@Slf4j
public class CountryServiceImpl implements CountryService {

    private ExternalFootballServiceConfiguration footballServiceConfig;
    private FootballApiClient footballApiClient;


    @Autowired
    public CountryServiceImpl(ExternalFootballServiceConfiguration footballServiceConfig, FootballApiClient footballApiClient) {
        this.footballServiceConfig = footballServiceConfig;
        this.footballApiClient = footballApiClient;
    }


    /**
     * this function calls the 3rd party api service to get the list of all countries
     * and  after getting the response check for its correctness
     * and then accordingly reply the caller with data or exception.
     *
     * @return list of CountryInfo
     */
    @Override
    public List<CountryInfo> getAllCountries() {

        String allCountries = footballApiClient.getAllCountries(FootballApiAction.GET_ALL_COUNTRIES.getActionValue(),
                footballServiceConfig.getApiKey());

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(String.valueOf(allCountries), new TypeReference<List<CountryInfo>>() {});
        } catch (Exception ex) {
            try {
                FootballApiErrorResponse errorResponse = mapper.readValue(String.valueOf(allCountries), new TypeReference<FootballApiErrorResponse>() {
                });
                switch (errorResponse.getMessage()) {
                    case FOOTBALL_SERVICE_AUTHENTICATION_FAILED_MSG ->
                            throw new FootballServiceAuthenticationFailedException(errorResponse.getMessage());
                    default -> throw new DefaultError(errorResponse.getMessage());
                }
            } catch (JsonProcessingException jex) {
                log.error("Exception occurred in processing response for getting all countries api : ", jex);
                throw new DefaultError("Something went wrong");
            }
        }
    }

    /**
     * this function calls the 3rd party api service to get the list of all countries
     * and then filter out the country by country name and return respective countryId
     *
     * @param countryName
     * @return countryId
     */
    @Override
    public String getCountryIdByCountryName(String countryName) {
        Optional<CountryInfo> countryInfoOptional = getAllCountries().stream().filter(countryInfo -> countryInfo.getCountryName().equals(countryName)).findFirst();
        if(countryInfoOptional.isPresent())
            return countryInfoOptional.get().getCountryId();
        throw new NoDataFoundException("country with name : " + countryName +" not found!");
    }
}
