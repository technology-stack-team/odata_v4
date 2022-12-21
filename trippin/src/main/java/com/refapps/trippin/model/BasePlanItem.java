package com.refapps.trippin.model;

import lombok.Data;
import com.refapps.trippin.converter.DurationConverter;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.Duration;
import java.util.Date;

@Entity(name = "BasePlanItem")
@Table(name = "\"BasePlanItem\"", schema =  "\"Trippin\"")
@Data
@Inheritance
@DiscriminatorColumn(name = "\"Type\"")
public abstract class BasePlanItem {
    public BasePlanItem() {}

    @Id
    @Column(name = "\"PlanItemId\"")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer planItemId;
    @Column(name = "\"ConfirmationCode\"")
    private String confirmationCode;
    @Column(name = "\"StartsAt\"", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startsAt;
    @Column(name = "\"EndsAt\"", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endsAt;
    @Convert(converter = DurationConverter.class)
    @Column(name = "\"Duration\"", nullable = false)
    private Duration duration;

    @Column(name = "\"Type\"", length = 1, insertable = false, updatable = false, nullable = false)
    protected String type;
    @Column(name = "\"TripId\"")
    private Integer tripId;
}
