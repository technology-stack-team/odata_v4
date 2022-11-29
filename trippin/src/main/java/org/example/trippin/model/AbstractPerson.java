package org.example.trippin.model;

import lombok.Data;

import javax.persistence.*;

@Entity(name = "AbstractPerson")
@Table(name = "\"Person\"", schema =  "\"Trippin\"")
@Data
@Inheritance
@DiscriminatorColumn(name = "\"DType\"")
public abstract class AbstractPerson {

    @Id
    @Column(name = "\"UserName\"")
    protected String userName;

    @Column(name = "\"DType\"", length = 1, insertable = false, updatable = false, nullable = false)
    protected String dType;
}
