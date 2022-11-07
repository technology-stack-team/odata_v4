package org.example.trippin.model.jointable;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.trippin.enums.Feature;

import javax.persistence.*;

@Entity(name = "PersonFeature")
@Table(schema = "Trippin", name = "PersonFeature")
@EdmIgnore
@Data
@NoArgsConstructor
public class PersonFeature {
    @Id
    @Column(name = "UserName")
    private String userName;

    @Column(name = "Features", nullable = false)
    @Enumerated(value = EnumType.ORDINAL)
    private Feature feature;
}
