package com.gr8erkay.klashaproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Country {
    private String name;
    private List<State> states;

    private List<PopulationCount> populationCounts;
    private String capitalCity;
    private String location;
    private String currency;
    private String iso2;
    private String iso3;





}
