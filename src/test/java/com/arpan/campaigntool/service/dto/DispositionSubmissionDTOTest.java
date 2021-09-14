package com.arpan.campaigntool.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DispositionSubmissionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositionSubmissionDTO.class);
        DispositionSubmissionDTO dispositionSubmissionDTO1 = new DispositionSubmissionDTO();
        dispositionSubmissionDTO1.setId(1L);
        DispositionSubmissionDTO dispositionSubmissionDTO2 = new DispositionSubmissionDTO();
        assertThat(dispositionSubmissionDTO1).isNotEqualTo(dispositionSubmissionDTO2);
        dispositionSubmissionDTO2.setId(dispositionSubmissionDTO1.getId());
        assertThat(dispositionSubmissionDTO1).isEqualTo(dispositionSubmissionDTO2);
        dispositionSubmissionDTO2.setId(2L);
        assertThat(dispositionSubmissionDTO1).isNotEqualTo(dispositionSubmissionDTO2);
        dispositionSubmissionDTO1.setId(null);
        assertThat(dispositionSubmissionDTO1).isNotEqualTo(dispositionSubmissionDTO2);
    }
}
