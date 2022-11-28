package org.example.trippin.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "Flight")
@Data
@DiscriminatorValue(value = "4")
public class Flight extends PublicTransportation {
    @Column(name = "\"FlightNumber\"")
    private String flightNumber;
    @JoinColumn(name = "\"FlightAirline\"", insertable = false, updatable = false, referencedColumnName = "\"AirlineCode\"")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {})
    private Airline airline;

    @JoinColumn(name = "\"FlightFrom\"", insertable = false, updatable = false, referencedColumnName = "\"IcaoCode\"")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {})
    private Airport from;

    @JoinColumn(name = "\"FlightTo\"", insertable = false, updatable = false, referencedColumnName = "\"IcaoCode\"")
    @ManyToOne(fetch = FetchType.LAZY, cascade = {})
    private Airport to;

    @Column(name = "\"FlightAirline\"")
    private String flightAirline;

    @Column(name = "\"FlightFrom\"")
    private String flightFrom;

    @Column(name = "\"FlightTo\"")
    private String flightTo;

}
