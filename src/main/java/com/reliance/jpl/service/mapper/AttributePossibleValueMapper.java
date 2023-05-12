package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.AttributeKey;
import com.reliance.jpl.domain.AttributePossibleValue;
import com.reliance.jpl.service.dto.AttributeKeyDTO;
import com.reliance.jpl.service.dto.AttributePossibleValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributePossibleValue} and its DTO {@link AttributePossibleValueDTO}.
 */
@Mapper(componentModel = "spring")
public interface AttributePossibleValueMapper extends EntityMapper<AttributePossibleValueDTO, AttributePossibleValue> {
    @Mapping(target = "key", source = "key", qualifiedByName = "attributeKeyId")
    AttributePossibleValueDTO toDto(AttributePossibleValue s);

    @Named("attributeKeyId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttributeKeyDTO toDtoAttributeKeyId(AttributeKey attributeKey);
}
