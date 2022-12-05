package com.refapps.trippin.model.jointable;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "PersonEmail")
@Table(schema = "\"Trippin\"", name = "\"PersonEmail\"")
@Data
public class PersonEmail {
    @Id
    @Column(name = "\"Id\"")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name = "PersonEmail.Id", sequenceName = "PersonEmail.Id", allocationSize = 1)
    private Integer id;

    @Column(name = "\"UserName\"")
    private String userName;

    @Column(name = "\"Emails\"")
    private String emailId;

}
