package com.sophos.bootcamp.bankapi.entities.converters;

import com.sophos.bootcamp.bankapi.entities.enums.MovementType;

import javax.persistence.AttributeConverter;
import java.util.Optional;

public class MovementTypeConverter implements AttributeConverter<MovementType, String> {

    @Override
    public String convertToDatabaseColumn(MovementType movementType) {
        return Optional.ofNullable(movementType).map(movementType1 -> movementType1.getStatus()).orElse(null);
    }

    @Override
    public MovementType convertToEntityAttribute(String s) {
        return Optional.ofNullable(s).map(m -> MovementType.valueOf(m)).orElse(null);
    }
}
