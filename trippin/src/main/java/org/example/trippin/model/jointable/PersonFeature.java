package org.example.trippin.model.jointable;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.trippin.converter.FeaturesConverter;
import org.example.trippin.enums.Feature;

import javax.persistence.*;

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
