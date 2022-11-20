package org.example.trippin.model.complex;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Data
public class EventLocation extends Location {
    public EventLocation() {
        super();
    }

    @Column(name = "\"BuildingInfo\"")
    private String buildingInfo;
}
