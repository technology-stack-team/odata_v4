package com.refapps.trippin.model;

import com.refapps.trippin.converter.UUIDToStringConverter;
import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(name = "Trip")
@Table(schema = "\"Trippin\"", name = "\"Trip\"")
@Data
public class Trip {
  @Id
  @Column(name = "\"TripId\"")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer tripId;

  @Column(name = "\"ShareId\"")
  @Convert(converter = UUIDToStringConverter.class)
  private UUID shareId;

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

  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
  @JoinColumn(name = "\"TripId\"", insertable = false, updatable = false)
  private List<PlanItem> planItems = new ArrayList<>();

  @ManyToMany(mappedBy = "trips")
  private List<Person> travellers;
}
