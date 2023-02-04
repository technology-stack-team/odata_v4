package com.refapps.trippin.model;

import lombok.Data;
import com.refapps.trippin.model.complex.AirportLocation;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity(name = "Airport")
@Table(name = "Airport",  schema =  "Trippin")
@Data
public class Airport {
    @Id
    @Column(name = "IcaoCode", nullable = false)
    private String icaoCode;

    @Version
    @Column(name = "ETag", nullable = false)
    protected long eTag;

    @Column(name = "Name")
    private String name;
    @Column(name = "IataCode")
    private String iataCode;
    @Embedded
    @Column(name =  "Location")
    private AirportLocation airportLocation;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longitude")
    private Double longitude;
    @Column(name = "IsInsideCity")
    private Boolean isInsideCity;

}
