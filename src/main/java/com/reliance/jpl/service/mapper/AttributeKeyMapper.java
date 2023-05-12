package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.AttributeKey;
import com.reliance.jpl.service.dto.AttributeKeyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributeKey} and its DTO {@link AttributeKeyDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttributeKeyMapper extends EntityMapper<AttributeKeyDTO, AttributeKey> {}
