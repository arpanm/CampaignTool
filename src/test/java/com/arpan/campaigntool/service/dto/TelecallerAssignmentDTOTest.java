package com.arpan.campaigntool.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelecallerAssignmentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelecallerAssignmentDTO.class);
        TelecallerAssignmentDTO telecallerAssignmentDTO1 = new TelecallerAssignmentDTO();
        telecallerAssignmentDTO1.setId(1L);
        TelecallerAssignmentDTO telecallerAssignmentDTO2 = new TelecallerAssignmentDTO();
        assertThat(telecallerAssignmentDTO1).isNotEqualTo(telecallerAssignmentDTO2);
        telecallerAssignmentDTO2.setId(telecallerAssignmentDTO1.getId());
        assertThat(telecallerAssignmentDTO1).isEqualTo(telecallerAssignmentDTO2);
        telecallerAssignmentDTO2.setId(2L);
        assertThat(telecallerAssignmentDTO1).isNotEqualTo(telecallerAssignmentDTO2);
        telecallerAssignmentDTO1.setId(null);
        assertThat(telecallerAssignmentDTO1).isNotEqualTo(telecallerAssignmentDTO2);
    }
}
