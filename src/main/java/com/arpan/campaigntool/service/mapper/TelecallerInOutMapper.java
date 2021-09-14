package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.TelecallerInOutDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TelecallerInOut} and its DTO {@link TelecallerInOutDTO}.
 */
@Mapper(componentModel = "spring", uses = { TelecallerMapper.class })
public interface TelecallerInOutMapper extends EntityMapper<TelecallerInOutDTO, TelecallerInOut> {
    @Mapping(target = "telecaller", source = "telecaller", qualifiedByName = "id")
    TelecallerInOutDTO toDto(TelecallerInOut s);
}
