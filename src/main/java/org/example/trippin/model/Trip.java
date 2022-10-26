package org.example.trippin.model;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity(name = "Trip")
@Table(schema = "Trippin", name = "Trip")
public class Trip {
  @Id
  @Column(name = "TripId")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TripId")
  @SequenceGenerator(name = "TripId", sequenceName = "Trippin.TripId", allocationSize = 1)
  private Integer tripId;

  @EdmIgnore
  @Column(name = "UserName")
  private String userName;

  @Column(name = "ShareId")
  private UUID shareId;

  @Column(name = "Name")
  private String name;

  @Column(name = "Budget")
  private Float budget;

  @Column(name = "Description")
  private String description;

  @Column(name = "StartsAt")
  private ZonedDateTime startsAt;

  @Column(name = "EndsAt")
  private ZonedDateTime endsAt;

  public Integer getTripId() {
    return tripId;
  }

  public void setTripId(Integer tripId) {
    this.tripId = tripId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public UUID getShareId() {
    return shareId;
  }

  public void setShareId(UUID shareId) {
    this.shareId = shareId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Float getBudget() {
    return budget;
  }

  public void setBudget(Float budget) {
    this.budget = budget;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ZonedDateTime getStartsAt() {
    return startsAt;
  }

  public void setStartsAt(ZonedDateTime startsAt) {
    this.startsAt = startsAt;
  }

  public ZonedDateTime getEndsAt() {
    return endsAt;
  }

  public void setEndsAt(ZonedDateTime endsAt) {
    this.endsAt = endsAt;
  }

}
