package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.TelecallerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Telecaller} and its DTO {@link TelecallerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TelecallerMapper extends EntityMapper<TelecallerDTO, Telecaller> {
    @Named("id")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TelecallerDTO toDtoId(Telecaller telecaller);
}
