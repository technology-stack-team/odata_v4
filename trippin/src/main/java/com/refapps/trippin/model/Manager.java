package com.refapps.trippin.model;

import lombok.Data;
import com.refapps.trippin.model.complex.Location;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Manager")
@Data
@DiscriminatorValue(value = "3")
public class Manager extends Person{
    @Column(name = "Budget", nullable = false)
    private Long budget;

    @Embedded
    @Column(name = "BossOffice")
    @AttributeOverrides({
            @AttributeOverride(name = "city.name", column = @Column(name = "BossOffice_CityName")),
            @AttributeOverride(name = "city.region", column = @Column(name = "BossOffice_CityRegion")),
            @AttributeOverride(name = "city.countryRegion", column = @Column(name = "BossOffice_CityCountryRegion")),
            @AttributeOverride(name = "address", column = @Column(name = "BossOffice_Address")),
            @AttributeOverride(name = "code", column = @Column(name = "BossOffice_Code"))
    })
    private Location bossOffice;


    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinTable(
            name="ManagerDirectReport",
            joinColumns = @JoinColumn( name="UserName"),
            inverseJoinColumns = @JoinColumn( name="DirectReport"), schema = "Trippin")
    private List<Person> directReports = new ArrayList<>();

}
