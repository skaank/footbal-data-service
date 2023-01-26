package com.project.footballApp.service.impl;

import com.project.footballApp.client.FootballApiClient;
import com.project.footballApp.exceptionHandling.custom.DefaultError;
import com.project.footballApp.exceptionHandling.custom.FootballServiceAuthenticationFailedException;
import com.project.footballApp.exceptionHandling.custom.NoDataFoundException;
import com.project.footballApp.model.CountryInfo;
import com.project.footballApp.service.CountryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@SpringBootTest
class CountryServiceImplTest {

    @Autowired
    private CountryService countryService;
    @MockBean
    private FootballApiClient footballApiClient;

    private String expectedCountriesResponse;

    @BeforeEach
    void setUp() {
        expectedCountriesResponse = """
                [
                    {
                        "country_id": "44",
                        "country_name": "England",
                        "country_logo": "https://apiv3.apifootball.com/badges/logo_country/44_england.png"
                    },
                    {
                        "country_id": "6",
                        "country_name": "Spain",
                        "country_logo": "https://apiv3.apifootball.com/badges/logo_country/6_spain.png"
                    },
                    {
                        "country_id": "3",
                        "country_name": "France",
                        "country_logo": "https://apiv3.apifootball.com/badges/logo_country/3_france.png"
                    }]""";
    }
    @Test
    void getAllCountries() {

        doReturn(expectedCountriesResponse).when(footballApiClient).getAllCountries(any(),any());

        // Execute the service call
        List<CountryInfo> countries = countryService.getAllCountries();

        // Assert the response
        Assertions.assertEquals(countries.size(), 3);
        Assertions.assertEquals(countries.get(0).getCountryId(), "44");
    }

    @Test
    void getAllCountriesAuthenticationFailedResponse() {

        expectedCountriesResponse = "{\"error\":404,\"message\":\"Authentification failed!\"}";
        doReturn(expectedCountriesResponse).when(footballApiClient).getAllCountries(any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(FootballServiceAuthenticationFailedException.class,() -> countryService.getAllCountries());
    }

    @Test
    void getAllCountriesUnExpectedResponse() {

        expectedCountriesResponse = "{\"error\":404,\"message\":\"some random failed!\"}";
        doReturn(expectedCountriesResponse).when(footballApiClient).getAllCountries(any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(DefaultError.class,() -> countryService.getAllCountries());
    }

    @Test
    void getCountryIdByCountryName() {
        doReturn(expectedCountriesResponse).when(footballApiClient).getAllCountries(any(),any());

        // Execute the service call
        String countryId = countryService.getCountryIdByCountryName("Spain");
        // Assert the response
        Assertions.assertEquals(countryId, "6");
        Assertions.assertThrows(NoDataFoundException.class,() -> countryService.getCountryIdByCountryName("India"));
    }

    @Test
    void getCountryIdByCountryNameWhenCountryNotFound() {
        doReturn(expectedCountriesResponse).when(footballApiClient).getAllCountries(any(),any());

        // Execute the service call and Assert the response
        Assertions.assertThrows(NoDataFoundException.class,() -> countryService.getCountryIdByCountryName("India"));
    }

}