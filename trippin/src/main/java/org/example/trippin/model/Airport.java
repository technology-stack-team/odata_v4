package org.example.trippin.model;

import lombok.Data;
import org.example.trippin.model.complex.AirportLocation;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Airport")
@Table(name = "\"Airport\"", schema = "Trippin")
@Data
public class Airport {
    @Id
    @Column(name = "\"IcaoCode\"", nullable = false)
    private String icaoCode;
    @Column(name = "\"Name\"")
    private String name;
    @Column(name = "\"IataCode\"")
    private String iataCode;
    @Embedded
    private AirportLocation location;
    @Column(name = "\"latitude\"")
    private Double latitude;
    @Column(name = "\"longitude\"")
    private Double longitude;
    @Column(name = "\"IsInsideCity\"")
    private Boolean isInsideCity;

}
