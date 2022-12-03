package com.refapps.trippin.model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Employee")
@Data
@DiscriminatorValue(value = "2")
public class Employee extends Person {
    @Column(name = "\"Cost\"", nullable = false)
    private Long cost;

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST})
    @JoinTable(
            name="\"PersonFriend\"",
            joinColumns = @JoinColumn( name="\"UserName\""),
            inverseJoinColumns = @JoinColumn( name="\"Friend\""), schema = "\"Trippin\"")
    private List<Person> peers = new ArrayList<>();
}
