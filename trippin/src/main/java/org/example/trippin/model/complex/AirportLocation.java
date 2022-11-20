package org.example.trippin.model.complex;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class AirportLocation {
    @Column(name = "\"Loc\"")
    private String Loc;
}