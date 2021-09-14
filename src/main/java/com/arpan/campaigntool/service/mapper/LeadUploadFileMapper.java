package com.arpan.campaigntool.service.mapper;

import com.arpan.campaigntool.domain.*;
import com.arpan.campaigntool.service.dto.LeadUploadFileDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link LeadUploadFile} and its DTO {@link LeadUploadFileDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface LeadUploadFileMapper extends EntityMapper<LeadUploadFileDTO, LeadUploadFile> {}
