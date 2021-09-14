package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.DispositionSubmissionValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DispositionSubmissionValue} and its DTO {@link DispositionSubmissionValueDTO}.
 */
@Mapper(componentModel = "spring", uses = { DispositionSubmissionMapper.class, FieldMapper.class, FieldPossibleValueMapper.class })
public interface DispositionSubmissionValueMapper extends EntityMapper<DispositionSubmissionValueDTO, DispositionSubmissionValue> {
    @Mapping(target = "dispositionSubmission", source = "dispositionSubmission", qualifiedByName = "id")
    @Mapping(target = "field", source = "field", qualifiedByName = "id")
    @Mapping(target = "possibleValue", source = "possibleValue", qualifiedByName = "id")
    DispositionSubmissionValueDTO toDto(DispositionSubmissionValue s);
}
