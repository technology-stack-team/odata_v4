package org.example.trippin.model;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;
import org.example.trippin.converter.FeaturesConverter;
import org.example.trippin.enums.Feature;
import org.example.trippin.enums.PersonGender;
import org.example.trippin.model.complex.Location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

@Entity(name = "Person")
@Table(schema = "Trippin", name = "Person")
@Data
public class Person {

  @Id
  @Column(name = "UserName")
  private String userName;

  @Column(name = "FirstName", nullable = false)
  private String firstName;

  @Column(name = "LastName", length = 26)
  private String lastName;

  @Column(name = "MiddleName")
  private String middleName;
  @Column(name = "Income",  precision = 30, scale = 5)
  private BigDecimal income;
  @Column(name = "DateOfBirth")
  @Temporal(value = TemporalType.DATE)
  private Date dateOfBirth;
  @Column(name = "Photo", length = 64000)
  private byte[] photo;
  @Column(name = "Gender", nullable = false)
  @Enumerated(value = EnumType.ORDINAL)
  private PersonGender Gender;
  @Column(name = "Age")
  private Short age;
  @Column(name = "Emails")
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(schema = "Trippin", name = "PersonEmail",
          joinColumns = @JoinColumn(name = "UserName"))
  private List<String> emails = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
  @JoinColumn(name = "UserName", insertable = false, updatable = false)
  private Collection<Trip> trips;

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(schema = "Trippin", name = "PersonAddressInfo",
          joinColumns = @JoinColumn(name = "UserName"))
  private List<Location> addressInfo;

  @Embedded
  @Column(name = "HomeAddress")
  @AttributeOverrides(value = {@AttributeOverride(name = "address", column = @Column(name = "HomeLocationAddress")), @AttributeOverride(name = "code", column = @Column(name = "HomeLocationCode"))})
  private Location homeAddress;

  @Column(name = "FavoriteFeature", nullable = false)
  @Enumerated(value = EnumType.ORDINAL)
  private Feature favouriteFeature;

  @Column(name = "Features")
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(schema = "Trippin", name = "PersonFeature",
          joinColumns = @JoinColumn(name = "UserName"))
  @Convert(converter = FeaturesConverter.class)
  List<Feature> features = new ArrayList<>();

  @JoinColumn(name = "BestFriend", insertable = false, updatable = false)
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  private Person bestFriend;



  @ManyToMany(fetch = FetchType.LAZY, cascade = {})
  @JoinTable(
          name="PersonFriend",
          joinColumns = @JoinColumn( name="UserName"),
          inverseJoinColumns = @JoinColumn( name="Friend"), schema = "Trippin")
  private Collection<Person> friends;

  @Column(name = "BestFriend")
  @EdmIgnore
  private String Friend;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getMiddleName() {
    return middleName;
  }

  public void setMiddleName(String middleName) {
    this.middleName = middleName;
  }

  public Short getAge() {
    return age;
  }

  public void setAge(Short age) {
    this.age = age;
  }

  public Collection<Trip> getTrips() {
    return trips;
  }

  public void setTrips(Collection<Trip> trips) {
    this.trips = trips;
  }

  @Override
  public int hashCode() {
    return Objects.hash(userName);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Person other = (Person) obj;
    return Objects.equals(userName, other.userName);
  }
}
