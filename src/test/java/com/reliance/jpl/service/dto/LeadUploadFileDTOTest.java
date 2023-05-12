package com.reliance.jpl.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.jpl.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeadUploadFileDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeadUploadFileDTO.class);
        LeadUploadFileDTO leadUploadFileDTO1 = new LeadUploadFileDTO();
        leadUploadFileDTO1.setId(1L);
        LeadUploadFileDTO leadUploadFileDTO2 = new LeadUploadFileDTO();
        assertThat(leadUploadFileDTO1).isNotEqualTo(leadUploadFileDTO2);
        leadUploadFileDTO2.setId(leadUploadFileDTO1.getId());
        assertThat(leadUploadFileDTO1).isEqualTo(leadUploadFileDTO2);
        leadUploadFileDTO2.setId(2L);
        assertThat(leadUploadFileDTO1).isNotEqualTo(leadUploadFileDTO2);
        leadUploadFileDTO1.setId(null);
        assertThat(leadUploadFileDTO1).isNotEqualTo(leadUploadFileDTO2);
    }
}
