package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.LeadDTO;
import java.util.Set;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lead} and its DTO {@link LeadDTO}.
 */
@Mapper(componentModel = "spring", uses = { LocationMapper.class })
public interface LeadMapper extends EntityMapper<LeadDTO, Lead> {
    @Mapping(target = "locations", source = "locations", qualifiedByName = "idSet")
    LeadDTO toDto(Lead s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LeadDTO toDtoId(Lead lead);

    @Mapping(target = "removeLocation", ignore = true)
    Lead toEntity(LeadDTO leadDTO);
}
