package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.TelecallerAssignmentDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TelecallerAssignment} and its DTO {@link TelecallerAssignmentDTO}.
 */
@Mapper(componentModel = "spring", uses = { TelecallerMapper.class, CampaignMapper.class })
public interface TelecallerAssignmentMapper extends EntityMapper<TelecallerAssignmentDTO, TelecallerAssignment> {
    @Mapping(target = "telecaller", source = "telecaller", qualifiedByName = "id")
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "id")
    TelecallerAssignmentDTO toDto(TelecallerAssignment s);
}
