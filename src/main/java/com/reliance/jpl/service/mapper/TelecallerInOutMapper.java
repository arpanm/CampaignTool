package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.Telecaller;
import com.reliance.jpl.domain.TelecallerInOut;
import com.reliance.jpl.service.dto.TelecallerDTO;
import com.reliance.jpl.service.dto.TelecallerInOutDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TelecallerInOut} and its DTO {@link TelecallerInOutDTO}.
 */
@Mapper(componentModel = "spring")
public interface TelecallerInOutMapper extends EntityMapper<TelecallerInOutDTO, TelecallerInOut> {
    @Mapping(target = "telecaller", source = "telecaller", qualifiedByName = "telecallerId")
    TelecallerInOutDTO toDto(TelecallerInOut s);

    @Named("telecallerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TelecallerDTO toDtoTelecallerId(Telecaller telecaller);
}
