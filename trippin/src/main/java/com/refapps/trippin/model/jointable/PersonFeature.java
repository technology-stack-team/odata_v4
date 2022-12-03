package com.refapps.trippin.model.jointable;

import lombok.Data;
import com.refapps.trippin.enums.Feature;

import javax.persistence.*;

@Entity(name = "PersonFeature")
@Table(schema = "\"Trippin\"", name = "\"PersonFeature\"")
@Data
public class PersonFeature {

    @Id
    @Column(name = "\"Id\"")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name = "PersonFeature.Id", sequenceName = "PersonFeature.Id", allocationSize = 1)
    private Integer id;

    @Column(name = "\"UserName\"")
    private String userName;

    @Column(name = "\"Features\"")
    @Enumerated(value = EnumType.ORDINAL)
    private Feature feature;
}
