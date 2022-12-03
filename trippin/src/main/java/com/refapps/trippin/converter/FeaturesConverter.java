package com.refapps.trippin.converter;

import com.refapps.trippin.enums.Feature;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class FeaturesConverter implements AttributeConverter<Feature, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Feature attributes) {
        return attributes.ordinal();
    }

    @Override
    public Feature convertToEntityAttribute(Integer dbData) {
        return Feature.getFeature(dbData);
    }

}
