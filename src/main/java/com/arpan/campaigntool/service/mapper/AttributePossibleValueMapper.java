package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.AttributePossibleValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link AttributePossibleValue} and its DTO {@link AttributePossibleValueDTO}.
 */
@Mapper(componentModel = "spring", uses = { AttributeKeyMapper.class })
public interface AttributePossibleValueMapper extends EntityMapper<AttributePossibleValueDTO, AttributePossibleValue> {
    @Mapping(target = "key", source = "key", qualifiedByName = "id")
    AttributePossibleValueDTO toDto(AttributePossibleValue s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    AttributePossibleValueDTO toDtoId(AttributePossibleValue attributePossibleValue);
}
