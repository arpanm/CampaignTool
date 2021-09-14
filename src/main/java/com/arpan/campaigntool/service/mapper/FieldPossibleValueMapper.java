package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.FieldPossibleValueDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link FieldPossibleValue} and its DTO {@link FieldPossibleValueDTO}.
 */
@Mapper(componentModel = "spring", uses = { FieldMapper.class })
public interface FieldPossibleValueMapper extends EntityMapper<FieldPossibleValueDTO, FieldPossibleValue> {
    @Mapping(target = "field", source = "field", qualifiedByName = "id")
    FieldPossibleValueDTO toDto(FieldPossibleValue s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldPossibleValueDTO toDtoId(FieldPossibleValue fieldPossibleValue);
}
