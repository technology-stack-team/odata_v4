package org.example.trippin.model.complex;

import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@Data
public class Location {
    @Column(name = "Address")
    private String address;

    @Column(name = "City")
    @AttributeOverrides(value =  {@AttributeOverride(name = "name", column = @Column(name = "LocationCityName")), @AttributeOverride(name = "countryRegion", column = @Column(name = "LocationCityCountryRegion")), @AttributeOverride(name = "region", column = @Column(name = "LocationCityRegion"))})
    @Embedded
    private City city;

    @Column(name = "Code")
    private Integer code;
}
