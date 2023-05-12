package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Call;
import com.reliance.jpl.domain.DispositionSubmission;
import com.reliance.jpl.domain.LeadAssignment;
import com.reliance.jpl.service.dto.CallDTO;
import com.reliance.jpl.service.dto.DispositionSubmissionDTO;
import com.reliance.jpl.service.dto.LeadAssignmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Call} and its DTO {@link CallDTO}.
 */
@Mapper(componentModel = "spring")
public interface CallMapper extends EntityMapper<CallDTO, Call> {
    @Mapping(target = "leadAssignment", source = "leadAssignment", qualifiedByName = "leadAssignmentId")
    @Mapping(target = "dispositionSubmission", source = "dispositionSubmission", qualifiedByName = "dispositionSubmissionId")
    CallDTO toDto(Call s);

    @Named("leadAssignmentId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeadAssignmentDTO toDtoLeadAssignmentId(LeadAssignment leadAssignment);

    @Named("dispositionSubmissionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositionSubmissionDTO toDtoDispositionSubmissionId(DispositionSubmission dispositionSubmission);
}
