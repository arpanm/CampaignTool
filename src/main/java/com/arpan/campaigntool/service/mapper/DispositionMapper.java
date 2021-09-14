package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.DispositionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Disposition} and its DTO {@link DispositionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DispositionMapper extends EntityMapper<DispositionDTO, Disposition> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    DispositionDTO toDtoId(Disposition disposition);
}
