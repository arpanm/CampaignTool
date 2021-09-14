package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.DispositionSubmissionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link DispositionSubmission} and its DTO {@link DispositionSubmissionDTO}.
 */
@Mapper(componentModel = "spring", uses = { DispositionMapper.class })
public interface DispositionSubmissionMapper extends EntityMapper<DispositionSubmissionDTO, DispositionSubmission> {
    @Mapping(target = "disposition", source = "disposition", qualifiedByName = "id")
    DispositionSubmissionDTO toDto(DispositionSubmission s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositionSubmissionDTO toDtoId(DispositionSubmission dispositionSubmission);
}
