package org.example.trippin.model;


import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmFunction;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmParameter;
import lombok.Data;
import org.example.trippin.converter.FeaturesConverter;
import org.example.trippin.converter.GenderConverter;
import org.example.trippin.enums.Feature;
import org.example.trippin.enums.PersonGender;
import org.example.trippin.model.complex.Location;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorValue;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity(name = "Person")
@Data
@DiscriminatorValue(value = "1")
@EdmFunction(
        name = "GetPersonWithMostFriends",
        functionName = "\"Trippin\".\"personWithMostFriends\"",
        isBound = false,
        hasFunctionImport = true,
        returnType = @EdmFunction.ReturnType(isCollection = false, type = Person.class)
       /* parameter = {
                @EdmParameter(name = "CodePublisher", parameterName = "\"Publisher\"",
                        type = String.class, maxLength = 10),
                @EdmParameter(name = "CodeID", parameterName = "\"ID\"", type = String.class, maxLength = 10),
                @EdmParameter(name = "DivisionCode", parameterName = "\"Division\"", type = String.class,
                        maxLength = 10) }*/)

public class Person extends AbstractPerson {
  public Person () {
    super();
    dType = "1";
  }

  @Column(name = "\"FirstName\"", nullable = false)
  private String firstName;

  @Column(name = "\"LastName\"", length = 26)
  private String lastName;

  @Column(name = "\"MiddleName\"")
  private String middleName;
  @Column(name = "\"Income\"",  precision = 30, scale = 5)
  private BigDecimal income;
  @Column(name = "\"DateOfBirth\"")
  @Temporal(value = TemporalType.DATE)
  private Date dateOfBirth;
  @Column(name = "\"Photo\"", length = 64000)
  private byte[] photo;
  @Column(name = "\"Gender\"", nullable = false)
  @Enumerated(value = EnumType.ORDINAL)
  @Convert(converter = GenderConverter.class)
  private PersonGender gender;
  @Column(name = "\"Age\"")
  private Short age;
  @Column(name = "\"Emails\"")
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(schema = "\"Trippin\"", name = "\"PersonEmail\"",
          joinColumns = @JoinColumn(name = "\"UserName\""))
  private List<String> emails = new ArrayList<>();

  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
  @JoinColumn(name = "\"UserName\"", insertable = false, updatable = false)
  private List<Trip> trips = new ArrayList<>();

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Person{" +
            "userName='" + userName + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", middleName='" + middleName + '\'' +
            ", income=" + income +
            ", dateOfBirth=" + dateOfBirth );
    if(photo != null)
        builder.append(", photo=" + Arrays.toString(photo));
    List<Person> friends =this.friends;
    if(friends != null)
      for(Person friend :  friends) {
        friend.setFriends(null);
      }
    this.setFriends(friends);
    Person bestFriend = this.bestFriend;
    if(bestFriend != null)
        bestFriend.setBestFriend(null);
    this.setBestFriend(bestFriend);
    builder.append( ", gender=" + gender +
            ", age=" + age +
            ", emails=" + emails +
            ", trips=" + trips +
            ", addressInfo=" + addressInfo +
            ", homeAddress=" + homeAddress +
            ", favoriteFeature=" + favoriteFeature +
            ", features=" + features +
            ", friends=" + friends +
            ", friend='" + friend + '\'' +
            '}');

    return builder.toString();
  }

  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(schema = "\"Trippin\"", name = "\"PersonAddressInfo\"",
          joinColumns = @JoinColumn(name = "\"UserName\"", updatable = true, insertable = true))
  private List<Location> addressInfo = new ArrayList<>();

  @Embedded
  @Column(name = "\"HomeAddress\"")
  @AttributeOverrides({
          @AttributeOverride(name = "city.name", column = @Column(name = "\"HomeAddress_CityName\"")),
          @AttributeOverride(name = "city.region", column = @Column(name = "\"HomeAddress_CityRegion\"")),
          @AttributeOverride(name = "city.countryRegion", column = @Column(name = "\"HomeAddress_CityCountryRegion\"")),
          @AttributeOverride(name = "address", column = @Column(name = "\"HomeAddress_Address\"")),
          @AttributeOverride(name = "code", column = @Column(name = "\"HomeAddress_Code\""))
  })
  private Location homeAddress;

  @Column(name = "\"FavoriteFeature\"", nullable = false)
  @Enumerated(value = EnumType.ORDINAL)
  @Convert(converter = FeaturesConverter.class)
  private Feature favoriteFeature;

  @Column(name = "\"Features\"")
  @ElementCollection(fetch = FetchType.LAZY)
  @CollectionTable(schema = "\"Trippin\"", name = "\"PersonFeature\"",
          joinColumns = @JoinColumn(name = "\"UserName\""))
  @Convert(converter = FeaturesConverter.class)
  List<Feature> features = new ArrayList<>();

  @JoinColumn(name = "\"Friend\"", insertable = false, updatable = false, referencedColumnName = "\"UserName\"")
  @ManyToOne(fetch = FetchType.LAZY, cascade = {})
  private Person bestFriend;


  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
  @JoinTable(
          name="\"PersonFriend\"",
          joinColumns = @JoinColumn( name="\"UserName\""),
          inverseJoinColumns = @JoinColumn( name="\"Friend\""), schema = "\"Trippin\"")
  private List<Person> friends = new ArrayList<>();

  @Column(name = "\"Friend\"")
  private String friend;

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