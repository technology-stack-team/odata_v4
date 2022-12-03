package com.refapps.trippin.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "PublicTransportation")
@Data
@DiscriminatorValue(value = "3")
public class PublicTransportation extends PlanItem{
    @Column(name = "\"SeatNo\"")
    private String seatNo;
}
