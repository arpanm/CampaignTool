package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Field;
import com.reliance.jpl.domain.FieldPossibleValue;
import com.reliance.jpl.service.dto.FieldDTO;
import com.reliance.jpl.service.dto.FieldPossibleValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FieldPossibleValue} and its DTO {@link FieldPossibleValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface FieldPossibleValueMapper extends EntityMapper<FieldPossibleValueDTO, FieldPossibleValue> {
    @Mapping(target = "field", source = "field", qualifiedByName = "fieldId")
    FieldPossibleValueDTO toDto(FieldPossibleValue s);

    @Named("fieldId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldDTO toDtoFieldId(Field field);
}
