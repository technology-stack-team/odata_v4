package org.example.trippin.model;

import lombok.Data;
import org.example.trippin.model.complex.EventLocation;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Inherited;

@Entity(name = "Event")
@Table(name = "\"BasePlanItem\"", schema = "\"Trippin\"")
@Data
public class Event extends BasePlanItem {
    @Column(name = "\"Description\"")
    private String description;
    @Column(name = "\"OccursAt\"")
    @Embedded
    private EventLocation eventLocation;
}
