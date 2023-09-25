package com.gr8erkay.countryproject.controller;

import com.gr8erkay.countryproject.entity.City;
import com.gr8erkay.countryproject.model.requestDTO.CityFilterRequest;
import com.gr8erkay.countryproject.model.requestDTO.CountryRequest;
import com.gr8erkay.countryproject.model.responseDTO.CountryResponse;
import com.gr8erkay.countryproject.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/city")
public class CountryController {

    @Autowired
    private CountryService countryService;

    @PostMapping("/most-populated")
    public List<City> getMostPopulatedCities(@RequestParam int N) {
        return countryService.getMostPopulatedCities(N);
    }

    @PostMapping("/country-info")
    public CountryResponse getCountryInfo(@RequestBody CountryRequest request) {
        return countryService.getCountryInfo(request.getCountry());
    }

    @PostMapping("/cities")
    public List<String> getCities(@RequestBody CityFilterRequest request) {
        return countryService.getCityInfo(request.getCountry());
    }

    @PostMapping("/cities-and-states")
    public CountryResponse getCitiesAndStates(@RequestBody CityFilterRequest request) {
        return countryService.getCitiesAndStatesByCountry(request.getCountry());
    }


    @GetMapping("/country-currency-converter")
    public CountryResponse getCountryCurrencyConverter(@RequestParam String country, Double amount, String currency) {
        return countryService.getCountryInfo(country);
    }
}
