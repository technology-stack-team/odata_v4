package org.example.trippin.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity(name = "Trip")
@Table(schema = "\"Trippin\"", name = "\"Trip\"")
@Data
public class Trip {
  @Id
  @Column(name = "\"TripId\"")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
//  @SequenceGenerator(name = "\"TripId\"", sequenceName = "Trippin.TripId", allocationSize = 1)
  private Integer tripId;

  @Column(name = "\"UserName\"")
  private String userName;

//  @Column(name = "ShareId")
//  @Convert(converter = UUIDToByteConverter.class)
//  private UUID shareId;

  @Column(name = "\"Name\"")
  private String name;

  @Column(name = "\"Budget\"")
  private Float budget;

  @Column(name = "\"Description\"")
  private String description;

  @Column(name = "\"StartsAt\"")
  @Temporal(TemporalType.TIMESTAMP)
  private Date startsAt;

  @Column(name = "\"EndsAt\"")
  @Temporal(TemporalType.TIMESTAMP)
  private Date endsAt;
}
