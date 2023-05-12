package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Telecaller;
import com.reliance.jpl.service.dto.TelecallerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telecaller} and its DTO {@link TelecallerDTO}.
 */
@Mapper(componentModel = "spring")
public interface TelecallerMapper extends EntityMapper<TelecallerDTO, Telecaller> {}
