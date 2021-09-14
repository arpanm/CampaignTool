package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.AttributeKeyDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributeKey} and its DTO {@link AttributeKeyDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AttributeKeyMapper extends EntityMapper<AttributeKeyDTO, AttributeKey> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttributeKeyDTO toDtoId(AttributeKey attributeKey);
}
