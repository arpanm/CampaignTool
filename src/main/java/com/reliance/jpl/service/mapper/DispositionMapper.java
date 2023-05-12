package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Disposition;
import com.reliance.jpl.service.dto.DispositionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Disposition} and its DTO {@link DispositionDTO}.
 */
@Mapper(componentModel = "spring")
public interface DispositionMapper extends EntityMapper<DispositionDTO, Disposition> {}
