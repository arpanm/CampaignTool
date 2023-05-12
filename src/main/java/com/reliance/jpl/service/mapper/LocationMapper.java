package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Location;
import com.reliance.jpl.service.dto.LocationDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Location} and its DTO {@link LocationDTO}.
 */
@Mapper(componentModel = "spring")
public interface LocationMapper extends EntityMapper<LocationDTO, Location> {}
