package com.arpan.campaigntool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeadUploadFileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeadUploadFile.class);
        LeadUploadFile leadUploadFile1 = new LeadUploadFile();
        leadUploadFile1.setId(1L);
        LeadUploadFile leadUploadFile2 = new LeadUploadFile();
        leadUploadFile2.setId(leadUploadFile1.getId());
        assertThat(leadUploadFile1).isEqualTo(leadUploadFile2);
        leadUploadFile2.setId(2L);
        assertThat(leadUploadFile1).isNotEqualTo(leadUploadFile2);
        leadUploadFile1.setId(null);
        assertThat(leadUploadFile1).isNotEqualTo(leadUploadFile2);
    }
}
