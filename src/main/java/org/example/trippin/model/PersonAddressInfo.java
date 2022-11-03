package org.example.trippin.model;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "PersonAddressInfo")
@Table(schema = "Trippin", name = "PersonAddressInfo")
@Data
@EdmIgnore
public class PersonAddressInfo {
    @Id
    @Column(name = "UserName")
    private String userName;
    @Embedded
    @Column(name = "AddressInfo")
    @AttributeOverrides(value = {@AttributeOverride(name = "address", column = @Column(name = "LocationAddress")), @AttributeOverride(name = "code", column = @Column(name = "LocationCode"))})
    private Location addressInfo;
}
