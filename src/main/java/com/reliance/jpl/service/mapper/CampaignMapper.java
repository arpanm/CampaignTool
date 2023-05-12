package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Campaign;
import com.reliance.jpl.domain.Client;
import com.reliance.jpl.domain.Disposition;
import com.reliance.jpl.service.dto.CampaignDTO;
import com.reliance.jpl.service.dto.ClientDTO;
import com.reliance.jpl.service.dto.DispositionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Campaign} and its DTO {@link CampaignDTO}.
 */
@Mapper(componentModel = "spring")
public interface CampaignMapper extends EntityMapper<CampaignDTO, Campaign> {
    @Mapping(target = "disposition", source = "disposition", qualifiedByName = "dispositionId")
    @Mapping(target = "client", source = "client", qualifiedByName = "clientId")
    CampaignDTO toDto(Campaign s);

    @Named("dispositionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositionDTO toDtoDispositionId(Disposition disposition);

    @Named("clientId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    ClientDTO toDtoClientId(Client client);
}
