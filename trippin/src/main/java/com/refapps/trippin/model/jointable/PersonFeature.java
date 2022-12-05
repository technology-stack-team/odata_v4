package com.refapps.trippin.model.jointable;

import com.refapps.trippin.converter.FeaturesConverter;
import lombok.Data;
import com.refapps.trippin.enums.Feature;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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
    @Convert(converter = FeaturesConverter.class)
    private Feature feature;
}
