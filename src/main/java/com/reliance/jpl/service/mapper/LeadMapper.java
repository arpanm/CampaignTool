package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Lead;
import com.reliance.jpl.domain.Location;
import com.reliance.jpl.service.dto.LeadDTO;
import com.reliance.jpl.service.dto.LocationDTO;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Lead} and its DTO {@link LeadDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeadMapper extends EntityMapper<LeadDTO, Lead> {
    @Mapping(target = "locations", source = "locations", qualifiedByName = "locationIdSet")
    LeadDTO toDto(Lead s);

    @Mapping(target = "removeLocation", ignore = true)
    Lead toEntity(LeadDTO leadDTO);

    @Named("locationId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    LocationDTO toDtoLocationId(Location location);

    @Named("locationIdSet")
    default Set<LocationDTO> toDtoLocationIdSet(Set<Location> location) {
        return location.stream().map(this::toDtoLocationId).collect(Collectors.toSet());
    }
}
