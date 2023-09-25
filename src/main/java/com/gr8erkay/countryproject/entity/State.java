package com.gr8erkay.countryproject.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class State {
    private String name;
    private List<City> cities;
    private Country country;
    private List<PopulationCount> populationCounts;


}
