package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.LeadAssignmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeadAssignment} and its DTO {@link LeadAssignmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { TelecallerMapper.class, LeadMapper.class })
public interface LeadAssignmentMapper extends EntityMapper<LeadAssignmentDTO, LeadAssignment> {
    @Mapping(target = "telecaller", source = "telecaller", qualifiedByName = "id")
    @Mapping(target = "lead", source = "lead", qualifiedByName = "id")
    LeadAssignmentDTO toDto(LeadAssignment s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeadAssignmentDTO toDtoId(LeadAssignment leadAssignment);
}
