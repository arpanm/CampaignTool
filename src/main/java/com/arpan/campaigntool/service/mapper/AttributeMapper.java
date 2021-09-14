package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.AttributeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Attribute} and its DTO {@link AttributeDTO}.
 */
@Mapper(
    componentModel = "spring",
    uses = { AttributePossibleValueMapper.class, AttributeKeyMapper.class, LeadMapper.class, CampaignMapper.class }
)
public interface AttributeMapper extends EntityMapper<AttributeDTO, Attribute> {
    @Mapping(target = "value", source = "value", qualifiedByName = "id")
    @Mapping(target = "key", source = "key", qualifiedByName = "id")
    @Mapping(target = "lead", source = "lead", qualifiedByName = "id")
    @Mapping(target = "campaign", source = "campaign", qualifiedByName = "id")
    AttributeDTO toDto(Attribute s);
}
