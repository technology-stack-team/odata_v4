package org.example.trippin.model.jointable;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "PersonFriend")
@Table(schema = "Trippin", name = "PersonFriend")
@EdmIgnore
@Data
public class PersonFriend {
    @Id
    @Column(name = "UserName")
    private String userName;

    @Column(name = "Friend")
    private String friend;
}
