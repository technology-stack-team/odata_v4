package org.example.trippin.model.jointable;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "TripPlanItem")
@Table(schema = "\"Trippin\"", name = "\"TripPlanItem\"")
@Data
public class TripPlanItem {
    @Id
    @Column(name = "\"Id\"")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"TripId\"")
    private Integer tripId;

    @Column(name = "\"PlanItemId\"")
    private Integer planItemId;
}
