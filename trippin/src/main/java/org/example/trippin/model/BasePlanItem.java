package org.example.trippin.model;

import lombok.Data;

import javax.persistence.*;
import java.time.Duration;
import java.util.Date;

@Entity(name = "BasePlanItem")
@Table(name = "\"PlanItem\"", schema = "Trippin")
@Data
public abstract class BasePlanItem {
    @Id
    @Column(name = "\"PlanItemId\"", nullable = false)
    private Integer PlanItemId;
    @Column(name = "\"ConfirmationCode\"")
    private String confirmationCode;
    @Column(name = "\"StartsAt\"", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date startsAt;
    @Column(name = "\"EndsAt\"", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date endsAt;
//    @Convert(converter = DurationConverter.class)
    @Column(name = "\"Duration\"", nullable = false)
    private Duration duration;
}
