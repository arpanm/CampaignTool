package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Campaign;
import com.reliance.jpl.domain.Lead;
import com.reliance.jpl.domain.LeadAssociation;
import com.reliance.jpl.service.dto.CampaignDTO;
import com.reliance.jpl.service.dto.LeadAssociationDTO;
import com.reliance.jpl.service.dto.LeadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeadAssociation} and its DTO {@link LeadAssociationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeadAssociationMapper extends EntityMapper<LeadAssociationDTO, LeadAssociation> {
    @Mapping(target = "lead", source = "lead", qualifiedByName = "leadId")
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "campaignId")
    LeadAssociationDTO toDto(LeadAssociation s);

    @Named("leadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeadDTO toDtoLeadId(Lead lead);

    @Named("campaignId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CampaignDTO toDtoCampaignId(Campaign campaign);
}
