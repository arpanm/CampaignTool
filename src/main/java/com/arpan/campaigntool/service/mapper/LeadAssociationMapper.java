package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.LeadAssociationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeadAssociation} and its DTO {@link LeadAssociationDTO}.
 */
@Mapper(componentModel = "spring", uses = { LeadMapper.class, CampaignMapper.class })
public interface LeadAssociationMapper extends EntityMapper<LeadAssociationDTO, LeadAssociation> {
    @Mapping(target = "lead", source = "lead", qualifiedByName = "id")
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "id")
    LeadAssociationDTO toDto(LeadAssociation s);
}
