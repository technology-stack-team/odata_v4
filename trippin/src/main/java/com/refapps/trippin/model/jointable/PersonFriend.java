package com.refapps.trippin.model.jointable;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name = "PersonFriend")
@Table(schema = "\"Trippin\"", name = "\"PersonFriend\"")
@Data
public class PersonFriend {

    @Id
    @Column(name = "\"Id\"")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name = "Trippin.PersonFriend.Id", sequenceName = "Trippin.PersonFriend.Id", allocationSize = 1)
    private Integer id;

    @Column(name = "\"UserName\"")
    private String userName;

    @Column(name = "\"Friend\"")
    private String friend;
}
