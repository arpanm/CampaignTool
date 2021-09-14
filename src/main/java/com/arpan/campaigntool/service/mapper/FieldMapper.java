package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.FieldDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Field} and its DTO {@link FieldDTO}.
 */
@Mapper(componentModel = "spring", uses = { DispositionMapper.class })
public interface FieldMapper extends EntityMapper<FieldDTO, Field> {
    @Mapping(target = "disposition", source = "disposition", qualifiedByName = "id")
    FieldDTO toDto(Field s);

    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    FieldDTO toDtoId(Field field);
}
