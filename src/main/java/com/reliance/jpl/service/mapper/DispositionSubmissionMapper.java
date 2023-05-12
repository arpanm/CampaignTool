package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Disposition;
import com.reliance.jpl.domain.DispositionSubmission;
import com.reliance.jpl.service.dto.DispositionDTO;
import com.reliance.jpl.service.dto.DispositionSubmissionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DispositionSubmission} and its DTO {@link DispositionSubmissionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DispositionSubmissionMapper extends EntityMapper<DispositionSubmissionDTO, DispositionSubmission> {
    @Mapping(target = "disposition", source = "disposition", qualifiedByName = "dispositionId")
    DispositionSubmissionDTO toDto(DispositionSubmission s);

    @Named("dispositionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositionDTO toDtoDispositionId(Disposition disposition);
}
