package com.arpan.campaigntool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DispositionSubmissionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositionSubmission.class);
        DispositionSubmission dispositionSubmission1 = new DispositionSubmission();
        dispositionSubmission1.setId(1L);
        DispositionSubmission dispositionSubmission2 = new DispositionSubmission();
        dispositionSubmission2.setId(dispositionSubmission1.getId());
        assertThat(dispositionSubmission1).isEqualTo(dispositionSubmission2);
        dispositionSubmission2.setId(2L);
        assertThat(dispositionSubmission1).isNotEqualTo(dispositionSubmission2);
        dispositionSubmission1.setId(null);
        assertThat(dispositionSubmission1).isNotEqualTo(dispositionSubmission2);
    }
}
