package org.example.trippin.converter;

import org.example.trippin.enums.PersonGender;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class GenderConverter implements AttributeConverter<PersonGender, Integer> {
    @Override
    public Integer convertToDatabaseColumn(PersonGender personGender) {
        return personGender.ordinal();
    }

    @Override
    public PersonGender convertToEntityAttribute(Integer integer) {
        for(PersonGender gender : PersonGender.values())
                if(gender.ordinal() == integer)
                        return gender;
        return null;
    }
}
