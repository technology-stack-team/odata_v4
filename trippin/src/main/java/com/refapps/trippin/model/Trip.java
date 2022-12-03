package com.refapps.trippin.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

  @Column(name = "\"Tag\"")
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(schema = "\"Trippin\"", name = "\"TripTag\"",
          joinColumns = @JoinColumn(name = "\"TripId\""))
  private List<String> tags = new ArrayList<>();

  @Column(name = "\"StartsAt\"")
  @Temporal(TemporalType.TIMESTAMP)
  private Date startsAt;

  @Column(name = "\"EndsAt\"")
  @Temporal(TemporalType.TIMESTAMP)
  private Date endsAt;

  @Column(name = "\"StartTime\"")
  @Temporal(TemporalType.TIME)
  private Date startTime;

  @Column(name = "\"EndTime\"")
  @Temporal(TemporalType.TIME)
  private Date endTime;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
  @JoinTable(
          name="\"TripPlanItem\"",
          joinColumns = @JoinColumn( name="\"TripId\""),
          inverseJoinColumns = @JoinColumn( name="\"PlanItemId\""), schema = "\"Trippin\"")
  private List<PlanItem> planItems = new ArrayList<>();
}
