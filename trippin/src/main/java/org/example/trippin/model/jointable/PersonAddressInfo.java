package org.example.trippin.model.jointable;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;
import org.example.trippin.model.complex.City;

import javax.persistence.*;


@Entity(name = "PersonAddressInfo")
@Table(schema = "\"Trippin\"", name = "\"PersonAddressInfo\"")
@Data
public class PersonAddressInfo {
    @Id
    @Column(name = "\"Id\"")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "\"UserName\"")
    private String userName;

    @Column(name = "\"City\"")
    @Embedded
    private City city;

    @Column(name = "\"Address\"")
    private String address;

    @Column(name = "\"Code\"")
    private Integer code;
}
