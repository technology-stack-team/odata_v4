package org.example.trippin.converter;

import org.example.trippin.enums.Feature;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class FeaturesConverter implements AttributeConverter<Feature, Short> {

    @Override
    public Short convertToDatabaseColumn(Feature attributes) {
        return attributes.getValue();
    }

    @Override
    public Feature convertToEntityAttribute(Short dbData) {
        for(Feature feature: Feature.values())
                if(feature.getValue() == dbData)
                        return feature;
        return null;
    }

}
