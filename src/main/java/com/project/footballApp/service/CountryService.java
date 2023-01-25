package com.project.footballApp.service;


import com.project.footballApp.model.CountryInfo;

import java.util.List;

public interface CountryService {

    List<CountryInfo> getAllCountries();
    String getCountryIdByCountryName(String countryName);
}
