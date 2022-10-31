package org.example.trippin.model;

import lombok.Data;

import javax.persistence.*;

@Embeddable
@Data
public class Location {
    @Column(name = "Address")
    private String address;
    @Column(name = "City")
    @Embedded
    @AttributeOverrides(value =  {@AttributeOverride(name = "name", column = @Column(name = "LocationCityName")), @AttributeOverride(name = "countryRegion", column = @Column(name = "LocationCityCountryRegion")), @AttributeOverride(name = "region", column = @Column(name = "LocationCityRegion"))})
    private City city;
    @Column(name = "Code")
    private Integer code;
}
