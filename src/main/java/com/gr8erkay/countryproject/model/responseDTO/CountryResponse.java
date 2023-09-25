package com.gr8erkay.countryproject.model.responseDTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CountryResponse {

//    ENDPOINT 02
//    https://countriesnow.space/api/v0.1/countries/currency
//    https://countriesnow.space/api/v0.1/countries/positions
//    https://countriesnow.space/api/v0.1/countries/capital
//    https://countriesnow.space/api/v0.1/countries/currency

//      ENDPOINT 03
//      https://countriesnow.space/api/v0.1/countries/cities -POST
//      https://countriesnow.space/api/v0.1/countries/states -POST


        private boolean error;
        private String msg;
        private CountryData data;

        @Getter
        @Setter
        public static class CountryData {
            private String name;
            private String iso3;
            private String iso2;
            private String currency;
            private Double lat;
            private Double longitude;
            private String capital;
            private double population;
            private List<State> states;
            private List<String> cities;
        }

        @Getter
        @Setter
        public static class State {
            private String name;
            private String stateCode;
        }

        @Getter
        @Setter
        public static class CountryPopulationData {
        private String country;
        private String code;
        private String iso3;
        private List<PopulationCount> populationCounts;
    }

        @Getter
        @Setter
        public static class PopulationCount {
            private int year;
            private long value;
        }
    }



