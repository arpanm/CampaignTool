package com.arpan.campaigntool.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeadAssignmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeadAssignmentDTO.class);
        LeadAssignmentDTO leadAssignmentDTO1 = new LeadAssignmentDTO();
        leadAssignmentDTO1.setId(1L);
        LeadAssignmentDTO leadAssignmentDTO2 = new LeadAssignmentDTO();
        assertThat(leadAssignmentDTO1).isNotEqualTo(leadAssignmentDTO2);
        leadAssignmentDTO2.setId(leadAssignmentDTO1.getId());
        assertThat(leadAssignmentDTO1).isEqualTo(leadAssignmentDTO2);
        leadAssignmentDTO2.setId(2L);
        assertThat(leadAssignmentDTO1).isNotEqualTo(leadAssignmentDTO2);
        leadAssignmentDTO1.setId(null);
        assertThat(leadAssignmentDTO1).isNotEqualTo(leadAssignmentDTO2);
    }
}
