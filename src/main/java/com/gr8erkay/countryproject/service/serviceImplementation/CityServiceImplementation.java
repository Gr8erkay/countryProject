package com.gr8erkay.countryproject.service.serviceImplementation;

import com.gr8erkay.countryproject.entity.City;
import com.gr8erkay.countryproject.model.requestDTO.CityFilterRequest;
import com.gr8erkay.countryproject.model.requestDTO.CountryRequest;
import com.gr8erkay.countryproject.model.responseDTO.CountryResponse;
import com.gr8erkay.countryproject.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CityServiceImplementation implements CountryService {

    @Value("${external.api.url}")
    private String apiUrl; // Set this in application.properties or application.yml

    private WebClient webClient;

    public CityServiceImplementation(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://countriesnow.space/api/v0.1/countries")
                .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                    if (clientResponse.statusCode().is3xxRedirection()) {
                        String newUrl = clientResponse.headers().header("Location").get(0);
                        return webClient.get().uri(newUrl).retrieve().bodyToMono(org.springframework.web.reactive.function.client.ClientResponse.class);
                    }
                    return Mono.just(clientResponse);
                }))
                .build();
    }

    @Autowired
    private WebClient.Builder webClientBuilder;

//    public CityServiceImplementation(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl(apiUrl).build();
//    }
public List<String> getCityInfo(String country) {
    // Create the request body with the country name
    Map<String, String> requestBody = new HashMap<>();
    requestBody.put("country", country);

    Flux<String> responseFlux = webClient.post()
            .uri("/cities")
            .bodyValue(requestBody)
            .retrieve()
            .bodyToFlux(String.class);

    return responseFlux.collectList().block();
}

    @Override
    public List<City> getMostPopulatedCities(int N) {
        CityFilterRequest requestBody = new CityFilterRequest();
        requestBody.setLimit(N);
        requestBody.setOrder("dsc");
        requestBody.setOrderBy("population");
        requestBody.setCountry("nigeria");
        System.out.println(requestBody.toString());
        return webClient.post()
                .uri("/population/cities/filter")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToFlux(City.class)
                .collectList()
                .block(); // Blocking call; handle differently in a reactive application
    }

//    @Override
//    public List<City> getMostPopulatedCities(int N) {
//        List<City> mostPopulatedCities = new ArrayList<>();
//
//        // Fetch cities from Italy, New Zealand, and Ghana
//        List<City> italyCities = countryService.getCitiesByCountry("Italy");
//        List<City> newZealandCities = countryService.getCitiesByCountry("New Zealand");
//        List<City> ghanaCities = countryService.getCitiesByCountry("Ghana");
//
//        // Combine all cities
//        List<City> allCities = new ArrayList<>();
//        allCities.addAll(italyCities);
//        allCities.addAll(newZealandCities);
//        allCities.addAll(ghanaCities);
//
//        // Sort by population in descending order
//        allCities.sort(Comparator.comparing(City::getPopulation).reversed());
//
//        // Get the most populated N cities
//        for (int i = 0; i < N && i < allCities.size(); i++) {
//            mostPopulatedCities.add(allCities.get(i));
//        }
//
//        return mostPopulatedCities;
//    }


//
//    public CountryResponse getCountryInfo(String country) {
//        CountryRequest countryRequest = new CountryRequest();
//        countryRequest.setCountry(country);
//        Mono<CountryResponse> responseMono = webClientBuilder.build()
//                .get()
//                .uri(uriBuilder -> uriBuilder
//                        .path(apiUrl + "/countries/population/country")
//                        .queryParam("country", country)
//                        .build())
//                .retrieve()
//                .bodyToMono(CountryResponse.class);
//
//        return responseMono.block();
//    }
//
//    private List<City> getCitiesFromApi() {
//        Mono<City[]> responseMono = webClientBuilder.build()
//                .get()
//                .uri(apiUrl + "/countries/population/cities")
//                .retrieve()
//                .bodyToMono(City[].class);
//
//        City[] citiesArray = responseMono.block();
//        return Arrays.asList(citiesArray);
//    }

    @Override
    public CountryResponse getCitiesAndStatesByCountry(String country) {
        // Create request bodies
        Mono<CountryResponse.CountryData> citiesResponseMono = webClient.post()
                .uri("/cities")
                .body(BodyInserters.fromValue(new CountryRequest(country)))
                .retrieve()
                .bodyToMono(CountryResponse.CountryData.class);

        Mono<CountryResponse.CountryData> statesResponseMono = webClient.post()
                .uri("/states")
                .body(BodyInserters.fromValue(new CountryRequest(country)))
                .retrieve()
                .bodyToMono(CountryResponse.CountryData.class);

        // Combine responses
        CountryResponse.CountryData citiesData = citiesResponseMono.block(); // Blocking call, handle differently in a reactive application
        CountryResponse.CountryData statesData = statesResponseMono.block(); // Blocking call, handle differently in a reactive application

        // Create and return the combined response
        return createCountryResponse(citiesData, statesData);
    }

    private CountryResponse createCountryResponse(CountryResponse.CountryData citiesData, CountryResponse.CountryData statesData) {
        CountryResponse.CountryData combinedData = new CountryResponse.CountryData();
        combinedData.setName(citiesData.getName());
        combinedData.setIso3(citiesData.getIso3());
        combinedData.setIso2(citiesData.getIso2());
        combinedData.setCities(citiesData.getCities());
        combinedData.setStates(statesData.getStates());

        CountryResponse countryResponse = new CountryResponse();
        countryResponse.setError(false);
        countryResponse.setMsg("Cities and states retrieved");
        countryResponse.setData(combinedData);

        return countryResponse;
    }

    @Override
    public CountryResponse getCountryInfo(String country) {
        Mono<CountryResponse.CountryData> currencyResponseMono = webClient.post()
                .uri("/currency")
                .body(BodyInserters.fromValue(new CountryRequest(country)))
                .retrieve()
                .bodyToMono(CountryResponse.CountryData.class);

        Mono<CountryResponse.CountryData> locationResponseMono = webClient.post()
                .uri("/positions")
                .body(BodyInserters.fromValue(new CountryRequest(country)))
                .retrieve()
                .bodyToMono(CountryResponse.CountryData.class);

        Mono<CountryResponse.CountryData> capitalResponseMono = webClient.post()
                .uri("/capital")
                .body(BodyInserters.fromValue(new CountryRequest(country)))
                .retrieve()
                .bodyToMono(CountryResponse.CountryData.class);

        Mono<CountryResponse.CountryPopulationData> populationResponseMono = webClient.post()
                .uri("/population")
                .body(BodyInserters.fromValue(new CountryRequest(country)))
                .retrieve()
                .bodyToMono(CountryResponse.CountryPopulationData.class);

        return Mono.zip(currencyResponseMono, locationResponseMono, capitalResponseMono, populationResponseMono)
                .map(tuple -> {
                    CountryResponse.CountryData currencyData = tuple.getT1();
                    CountryResponse.CountryData locationData = tuple.getT2();
                    CountryResponse.CountryData capitalData = tuple.getT3();
                    CountryResponse.CountryPopulationData populationResponse = tuple.getT4();

                    CountryResponse.CountryData combinedData = new CountryResponse.CountryData();
                    combinedData.setName(country);
                    combinedData.setCurrency(currencyData.getCurrency());
                    combinedData.setIso2(currencyData.getIso2());
                    combinedData.setIso3(currencyData.getIso3());
                    combinedData.setLat(locationData.getLat());
                    combinedData.setLongitude(locationData.getLongitude());
                    combinedData.setCapital(capitalData.getCapital());

                    if (populationResponse != null && populationResponse.getPopulationCounts() != null && !populationResponse.getPopulationCounts().isEmpty()) {
                        combinedData.setPopulation(populationResponse.getPopulationCounts().get(0).getValue());
                    }

                    return combinedData;
                })
                .map(data -> {
                    CountryResponse countryResponse = new CountryResponse();
                    countryResponse.setError(false);
                    countryResponse.setMsg("Country information retrieved");
                    countryResponse.setData(data);
                    return countryResponse;
                })
                .block(); // Blocking call, handle differently in a reactive application
    }
}

