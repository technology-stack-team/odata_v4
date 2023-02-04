package com.refapps.trippin.model.jointable;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "TripTag")
@Table(schema = "Trippin", name = "TripTag")
@Data
public class TripTag {
    @Id
    @Column(name = "Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "TripId")
    private Integer tripId;

    @Column(name = "Tag")
    private String tag;
}
