package org.example.trippin.model;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "PersonEmail")
@Table(schema = "Trippin", name = "PersonEmail")
@EdmIgnore
@Data
public class PersonEmail {
    @Id
    @Column(name = "UserName")
    private String userName;

    @Column(name = "Emails")
    private String emailId;

}
