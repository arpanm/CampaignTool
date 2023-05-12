package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Campaign;
import com.reliance.jpl.domain.Telecaller;
import com.reliance.jpl.domain.TelecallerAssignment;
import com.reliance.jpl.service.dto.CampaignDTO;
import com.reliance.jpl.service.dto.TelecallerAssignmentDTO;
import com.reliance.jpl.service.dto.TelecallerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TelecallerAssignment} and its DTO {@link TelecallerAssignmentDTO}.
 */
@Mapper(componentModel = "spring")
public interface TelecallerAssignmentMapper extends EntityMapper<TelecallerAssignmentDTO, TelecallerAssignment> {
    @Mapping(target = "telecaller", source = "telecaller", qualifiedByName = "telecallerId")
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "campaignId")
    TelecallerAssignmentDTO toDto(TelecallerAssignment s);

    @Named("telecallerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TelecallerDTO toDtoTelecallerId(Telecaller telecaller);

    @Named("campaignId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CampaignDTO toDtoCampaignId(Campaign campaign);
}
