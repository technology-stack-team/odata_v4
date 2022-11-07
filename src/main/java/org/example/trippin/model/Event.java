package org.example.trippin.model;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;
import org.example.trippin.model.complex.EventLocation;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Event")
@Table(name = "Event", schema = "Trippin")
@Data
public class Event {
    @Id
    @Column(name = "EventId")
    private Integer eventId;
    @Column(name = "Description")
    private String description;
    @Column(name = "OccursAt")
    @Embedded
    //@AttributeOverrides(value = {@AttributeOverride(name = "address", column = @Column(name = "EventLocationAddress")), @AttributeOverride(name = "code", column = @Column(name = "EventLocationCode")), @AttributeOverride(name = "buildingInfo", column = @Column(name = "EventBuildingInfo"))})
    private EventLocation eventLocation;
}
