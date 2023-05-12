package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Lead;
import com.reliance.jpl.domain.LeadAssignment;
import com.reliance.jpl.domain.Telecaller;
import com.reliance.jpl.service.dto.LeadAssignmentDTO;
import com.reliance.jpl.service.dto.LeadDTO;
import com.reliance.jpl.service.dto.TelecallerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeadAssignment} and its DTO {@link LeadAssignmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeadAssignmentMapper extends EntityMapper<LeadAssignmentDTO, LeadAssignment> {
    @Mapping(target = "telecaller", source = "telecaller", qualifiedByName = "telecallerId")
    @Mapping(target = "lead", source = "lead", qualifiedByName = "leadId")
    LeadAssignmentDTO toDto(LeadAssignment s);

    @Named("telecallerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TelecallerDTO toDtoTelecallerId(Telecaller telecaller);

    @Named("leadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeadDTO toDtoLeadId(Lead lead);
}
