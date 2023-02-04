package com.refapps.trippin.model.jointable;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "PersonTrip")
@Table(schema = "Trippin", name = "PersonTrip")
@Data
public class PersonTrip {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "TripId")
    private Integer tripId;
}
