package org.example.trippin.model.jointable;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;
import org.example.trippin.model.complex.City;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity(name = "PersonAddressInfo")
@Table(schema = "Trippin", name = "PersonAddressInfo")
@Data
@EdmIgnore
public class PersonAddressInfo {
    @Id
    @Column(name = "UserName")
    private String userName;

    @Column(name = "City")
    @AttributeOverrides(value =  {@AttributeOverride(name = "name", column = @Column(name = "LocationCityName")), @AttributeOverride(name = "countryRegion", column = @Column(name = "LocationCityCountryRegion")), @AttributeOverride(name = "region", column = @Column(name = "LocationCityRegion"))})
    @Embedded
    private City city;

    @Column(name = "LocationAddress")
    private String address;

    @Column(name = "LocationCode")
    private Integer code;
}
