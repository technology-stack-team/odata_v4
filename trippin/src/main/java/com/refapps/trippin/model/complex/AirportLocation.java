package com.refapps.trippin.model.complex;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class AirportLocation extends Location {
    public AirportLocation() {
        super();
    }
    @Column(name = "\"Loc\"")
    private String loc;
}