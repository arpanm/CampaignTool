package com.reliance.jpl.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.jpl.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DispositionSubmissionValueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositionSubmissionValueDTO.class);
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO1 = new DispositionSubmissionValueDTO();
        dispositionSubmissionValueDTO1.setId(1L);
        DispositionSubmissionValueDTO dispositionSubmissionValueDTO2 = new DispositionSubmissionValueDTO();
        assertThat(dispositionSubmissionValueDTO1).isNotEqualTo(dispositionSubmissionValueDTO2);
        dispositionSubmissionValueDTO2.setId(dispositionSubmissionValueDTO1.getId());
        assertThat(dispositionSubmissionValueDTO1).isEqualTo(dispositionSubmissionValueDTO2);
        dispositionSubmissionValueDTO2.setId(2L);
        assertThat(dispositionSubmissionValueDTO1).isNotEqualTo(dispositionSubmissionValueDTO2);
        dispositionSubmissionValueDTO1.setId(null);
        assertThat(dispositionSubmissionValueDTO1).isNotEqualTo(dispositionSubmissionValueDTO2);
    }
}
