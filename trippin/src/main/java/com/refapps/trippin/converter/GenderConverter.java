package com.refapps.trippin.converter;

import com.refapps.trippin.enums.PersonGender;
import com.refapps.trippin.model.Person;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = false)
public class GenderConverter implements AttributeConverter<PersonGender, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PersonGender personGender) {
        return personGender != null ? personGender.ordinal() : null;
    }

    @Override
    public PersonGender convertToEntityAttribute(Integer integer) {
        for(PersonGender gender : PersonGender.values())
                if(gender.ordinal() == integer)
                        return gender;
        return null;
    }
}
