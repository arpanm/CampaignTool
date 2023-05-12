package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Attribute;
import com.reliance.jpl.domain.AttributeKey;
import com.reliance.jpl.domain.AttributePossibleValue;
import com.reliance.jpl.domain.Campaign;
import com.reliance.jpl.domain.Lead;
import com.reliance.jpl.service.dto.AttributeDTO;
import com.reliance.jpl.service.dto.AttributeKeyDTO;
import com.reliance.jpl.service.dto.AttributePossibleValueDTO;
import com.reliance.jpl.service.dto.CampaignDTO;
import com.reliance.jpl.service.dto.LeadDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attribute} and its DTO {@link AttributeDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttributeMapper extends EntityMapper<AttributeDTO, Attribute> {
    @Mapping(target = "value", source = "value", qualifiedByName = "attributePossibleValueId")
    @Mapping(target = "key", source = "key", qualifiedByName = "attributeKeyId")
    @Mapping(target = "lead", source = "lead", qualifiedByName = "leadId")
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "campaignId")
    AttributeDTO toDto(Attribute s);

    @Named("attributePossibleValueId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttributePossibleValueDTO toDtoAttributePossibleValueId(AttributePossibleValue attributePossibleValue);

    @Named("attributeKeyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttributeKeyDTO toDtoAttributeKeyId(AttributeKey attributeKey);

    @Named("leadId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeadDTO toDtoLeadId(Lead lead);

    @Named("campaignId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CampaignDTO toDtoCampaignId(Campaign campaign);
}
