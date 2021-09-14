package com.arpan.campaigntool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelecallerAssignmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelecallerAssignment.class);
        TelecallerAssignment telecallerAssignment1 = new TelecallerAssignment();
        telecallerAssignment1.setId(1L);
        TelecallerAssignment telecallerAssignment2 = new TelecallerAssignment();
        telecallerAssignment2.setId(telecallerAssignment1.getId());
        assertThat(telecallerAssignment1).isEqualTo(telecallerAssignment2);
        telecallerAssignment2.setId(2L);
        assertThat(telecallerAssignment1).isNotEqualTo(telecallerAssignment2);
        telecallerAssignment1.setId(null);
        assertThat(telecallerAssignment1).isNotEqualTo(telecallerAssignment2);
    }
}
