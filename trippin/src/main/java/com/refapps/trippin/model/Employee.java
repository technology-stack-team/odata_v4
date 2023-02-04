package com.refapps.trippin.model;

import lombok.Data;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Employee")
@Data
@DiscriminatorValue(value = "2")
public class Employee extends Person {
    @Column(name = "Cost", nullable = false)
    private Long cost;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JoinTable(
            name="EmployeePeer",
            joinColumns = @JoinColumn( name="UserName"),
            inverseJoinColumns = @JoinColumn( name="Peer"), schema = "Trippin")
    private List<Person> peers = new ArrayList<>();
}
