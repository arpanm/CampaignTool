package com.reliance.jpl.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.jpl.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeadAssignmentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeadAssignment.class);
        LeadAssignment leadAssignment1 = new LeadAssignment();
        leadAssignment1.setId(1L);
        LeadAssignment leadAssignment2 = new LeadAssignment();
        leadAssignment2.setId(leadAssignment1.getId());
        assertThat(leadAssignment1).isEqualTo(leadAssignment2);
        leadAssignment2.setId(2L);
        assertThat(leadAssignment1).isNotEqualTo(leadAssignment2);
        leadAssignment1.setId(null);
        assertThat(leadAssignment1).isNotEqualTo(leadAssignment2);
    }
}
