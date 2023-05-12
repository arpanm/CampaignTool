package com.reliance.jpl.service.mapper;

import com.reliance.jpl.domain.LeadUploadFile;
import com.reliance.jpl.service.dto.LeadUploadFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeadUploadFile} and its DTO {@link LeadUploadFileDTO}.
 */
@Mapper(componentModel = "spring")
public interface LeadUploadFileMapper extends EntityMapper<LeadUploadFileDTO, LeadUploadFile> {}
