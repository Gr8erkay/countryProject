package com.gr8erkay.countryproject.controller;

import com.gr8erkay.countryproject.entity.City;
import com.gr8erkay.countryproject.model.requestDTO.CityFilterRequest;
import com.gr8erkay.countryproject.model.requestDTO.CountryRequest;
import com.gr8erkay.countryproject.model.responseDTO.CountryResponse;
import com.gr8erkay.countryproject.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/city")
@Tag(
        name = "Country Endpoints"
)
public class CountryController {

    @Autowired
    private CountryService countryService;

    @Operation(
            summary = "Get Most Populated Cities",
            description = "Enter the number of cities you require"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cities Fetch Successful, here are a list of the most populated cities"
    )
    @PostMapping("/most-populated")
    public List<City> getMostPopulatedCities(@RequestParam int N) {
        return countryService.getMostPopulatedCities(N);
    }

    @Operation(
            summary = "Get Most Populated Cities",
            description = "Enter the number of cities you require"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cities Fetch Successful, here are a list of the most populated cities"
    )
    @PostMapping("/country-info")
    public CountryResponse getCountryInfo(@RequestBody CountryRequest request) {
        return countryService.getCountryInfo(request.getCountry());
    }

    @Operation(
            summary = "Get Cities in a country",
            description = "Enter the country name"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cities Fetch Successful, here are the details of your country"
    )
    @PostMapping("/cities")
    public List<String> getCities(@RequestBody CityFilterRequest request) {
        return countryService.getCityInfo(request.getCountry());
    }

    @Operation(
            summary = "Get List of Cities and States in a Country",
            description = "Enter the name of the country"
    )
    @ApiResponse(
            responseCode = "200",
            description = "Cities and State Fetch Successful, here are a list of the requested cities and states"
    )
    @PostMapping("/cities-and-states")
    public CountryResponse getCitiesAndStates(@RequestBody CityFilterRequest request) {
        return countryService.getCitiesAndStatesByCountry(request.getCountry());
    }


    @GetMapping("/country-currency-converter")
    public CountryResponse getCountryCurrencyConverter(@RequestParam String country, Double amount, String currency) {
        return countryService.getCountryInfo(country);
    }
}
