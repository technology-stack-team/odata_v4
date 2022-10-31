package org.example.trippin.model;

import liquibase.datatype.DataTypeInfo;
import lombok.Data;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
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
