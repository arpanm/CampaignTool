package com.arpan.campaigntool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DispositionSubmissionValueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositionSubmissionValue.class);
        DispositionSubmissionValue dispositionSubmissionValue1 = new DispositionSubmissionValue();
        dispositionSubmissionValue1.setId(1L);
        DispositionSubmissionValue dispositionSubmissionValue2 = new DispositionSubmissionValue();
        dispositionSubmissionValue2.setId(dispositionSubmissionValue1.getId());
        assertThat(dispositionSubmissionValue1).isEqualTo(dispositionSubmissionValue2);
        dispositionSubmissionValue2.setId(2L);
        assertThat(dispositionSubmissionValue1).isNotEqualTo(dispositionSubmissionValue2);
        dispositionSubmissionValue1.setId(null);
        assertThat(dispositionSubmissionValue1).isNotEqualTo(dispositionSubmissionValue2);
    }
}
