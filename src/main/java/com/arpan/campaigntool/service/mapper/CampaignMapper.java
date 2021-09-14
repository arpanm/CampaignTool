package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.CampaignDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Campaign} and its DTO {@link CampaignDTO}.
 */
@Mapper(componentModel = "spring", uses = { DispositionMapper.class, ClientMapper.class })
public interface CampaignMapper extends EntityMapper<CampaignDTO, Campaign> {
    @Mapping(target = "disposition", source = "disposition", qualifiedByName = "id")
    @Mapping(target = "client", source = "client", qualifiedByName = "id")
    CampaignDTO toDto(Campaign s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    CampaignDTO toDtoId(Campaign campaign);
}
