package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Disposition;
import com.reliance.jpl.domain.Field;
import com.reliance.jpl.service.dto.DispositionDTO;
import com.reliance.jpl.service.dto.FieldDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Field} and its DTO {@link FieldDTO}.
 */
@Mapper(componentModel = "spring")
public interface FieldMapper extends EntityMapper<FieldDTO, Field> {
    @Mapping(target = "disposition", source = "disposition", qualifiedByName = "dispositionId")
    FieldDTO toDto(Field s);

    @Named("dispositionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositionDTO toDtoDispositionId(Disposition disposition);
}
