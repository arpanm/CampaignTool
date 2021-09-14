package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.CallDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Call} and its DTO {@link CallDTO}.
 */
@Mapper(componentModel = "spring", uses = { LeadAssignmentMapper.class, DispositionSubmissionMapper.class })
public interface CallMapper extends EntityMapper<CallDTO, Call> {
    @Mapping(target = "leadAssignment", source = "leadAssignment", qualifiedByName = "id")
    @Mapping(target = "dispositionSubmission", source = "dispositionSubmission", qualifiedByName = "id")
    CallDTO toDto(Call s);
}
