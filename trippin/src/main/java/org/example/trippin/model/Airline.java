package org.example.trippin.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Airline")
@Table(name = "\"Airline\"",  schema =  "\"Trippin\"")
@Data
public class Airline {
    @Id
    @Column(name = "\"AirlineCode\"")
    private String airlineCode;

    @Column(name = "\"Name\"")
    private String name;


}
