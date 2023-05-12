package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.DispositionSubmission;
import com.reliance.jpl.domain.DispositionSubmissionValue;
import com.reliance.jpl.domain.Field;
import com.reliance.jpl.domain.FieldPossibleValue;
import com.reliance.jpl.service.dto.DispositionSubmissionDTO;
import com.reliance.jpl.service.dto.DispositionSubmissionValueDTO;
import com.reliance.jpl.service.dto.FieldDTO;
import com.reliance.jpl.service.dto.FieldPossibleValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DispositionSubmissionValue} and its DTO {@link DispositionSubmissionValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface DispositionSubmissionValueMapper extends EntityMapper<DispositionSubmissionValueDTO, DispositionSubmissionValue> {
    @Mapping(target = "dispositionSubmission", source = "dispositionSubmission", qualifiedByName = "dispositionSubmissionId")
    @Mapping(target = "field", source = "field", qualifiedByName = "fieldId")
    @Mapping(target = "possibleValue", source = "possibleValue", qualifiedByName = "fieldPossibleValueId")
    DispositionSubmissionValueDTO toDto(DispositionSubmissionValue s);

    @Named("dispositionSubmissionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositionSubmissionDTO toDtoDispositionSubmissionId(DispositionSubmission dispositionSubmission);

    @Named("fieldId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldDTO toDtoFieldId(Field field);

    @Named("fieldPossibleValueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldPossibleValueDTO toDtoFieldPossibleValueId(FieldPossibleValue fieldPossibleValue);
}
