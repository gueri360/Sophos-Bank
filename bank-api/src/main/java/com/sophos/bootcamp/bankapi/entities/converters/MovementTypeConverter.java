package com.sophos.bootcamp.bankapi.entities.converters;

import com.sophos.bootcamp.bankapi.entities.enums.MovementType;

import javax.persistence.AttributeConverter;

public class MovementTypeConverter implements AttributeConverter<MovementType, String> {

    @Override
    public String convertToDatabaseColumn(MovementType movementType) {
        return movementType.getStatus();
    }

    @Override
    public MovementType convertToEntityAttribute(String s) {
        return MovementType.valueOf(s);
    }
}
