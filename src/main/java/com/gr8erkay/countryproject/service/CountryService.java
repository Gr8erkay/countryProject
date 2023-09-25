package com.gr8erkay.countryproject.service;

import com.gr8erkay.countryproject.entity.City;
import com.gr8erkay.countryproject.model.responseDTO.CountryResponse;
import reactor.core.publisher.Mono;

import java.util.List;

public interface CountryService {

    List<String> getCityInfo(String cityName);

    List<City> getMostPopulatedCities(int N);
    CountryResponse getCountryInfo(String country);

    CountryResponse getCitiesAndStatesByCountry(String country);


}
