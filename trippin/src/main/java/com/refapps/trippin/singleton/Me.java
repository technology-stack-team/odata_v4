package com.refapps.trippin.singleton;

import com.refapps.trippin.model.Person;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmEntityType;
import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmTopLevelElementRepresentation;
import lombok.Data;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity(name = "Me")
@EdmEntityType(as = EdmTopLevelElementRepresentation.AS_SINGLETON_ONLY)
@Data
@DiscriminatorValue(value = "5")
public class Me extends Person {
    public Me() {
        this.userName = "rahuljain";
        dType = "5";
    }

}
