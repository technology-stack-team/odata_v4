package org.example.trippin.model;

import lombok.Data;
import org.example.trippin.model.complex.Location;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Manager")
@Data
@DiscriminatorValue(value = "3")
public class Manager extends Person{
    @Column(name = "\"Budget\"", nullable = false)
    private Long budget;

    @Embedded
    @Column(name = "\"BossOffice\"")
    @AttributeOverrides({
            @AttributeOverride(name = "city.name", column = @Column(name = "\"BossOffice_CityName\"")),
            @AttributeOverride(name = "city.region", column = @Column(name = "\"BossOffice_CityRegion\"")),
            @AttributeOverride(name = "city.countryRegion", column = @Column(name = "\"BossOffice_CityCountryRegion\"")),
            @AttributeOverride(name = "address", column = @Column(name = "\"BossOffice_Address\"")),
            @AttributeOverride(name = "code", column = @Column(name = "\"BossOffice_Code\""))
    })
    private Location bossOffice;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name="\"PersonFriend\"",
            joinColumns = @JoinColumn( name="\"UserName\""),
            inverseJoinColumns = @JoinColumn( name="\"Friend\""), schema = "\"Trippin\"")
    private List<Person> directReports = new ArrayList<>();

}
