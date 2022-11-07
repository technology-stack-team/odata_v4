package org.example.trippin.model.complex;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class City {
    @Column(name = "Name")
    private String name;
    @Column(name = "CountryRegion")
    private String countryRegion;
    @Column(name = "Region")
    private String region;
}
