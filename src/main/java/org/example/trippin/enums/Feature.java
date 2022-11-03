package org.example.trippin.enums;

import com.sap.olingo.jpa.metadata.core.edm.annotation.EdmEnumeration;
import lombok.Getter;
import lombok.Setter;

@EdmEnumeration()
public enum Feature {
    Feature1((short)0),Feature2((short)1),Feature3((short)2),Feature4((short)3);

    Feature(short value) {
        this.value = value;
    }

    @Getter
    @Setter
    private short value;

}
