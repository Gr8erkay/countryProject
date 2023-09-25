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
public class City {
    private String city;
    private String country;
    private List<PopulationCount> populationCounts;


}
