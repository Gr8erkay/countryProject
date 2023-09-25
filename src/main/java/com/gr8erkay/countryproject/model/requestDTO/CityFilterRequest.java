package com.gr8erkay.countryproject.model.requestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CityFilterRequest {

    private int limit;
    private String order;
    private String orderBy;
    private String country;

    @Override
    public String toString() {
        return "CityFilterRequest{" +
                "limit=" + limit +
                ", order='" + order + '\'' +
                ", orderBy='" + orderBy + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
