package com.refapps.trippin.model;

import lombok.Data;
import com.refapps.trippin.model.complex.EventLocation;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity(name = "Event")
@Data
@DiscriminatorValue(value = "2")
public class Event extends PlanItem {
    @Column(name = "\"Description\"")
    private String description;
    @Column(name = "\"OccursAt\"")
    @Embedded
    private EventLocation eventLocation;
}
