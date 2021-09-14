package com.arpan.campaigntool.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeadAssociationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeadAssociationDTO.class);
        LeadAssociationDTO leadAssociationDTO1 = new LeadAssociationDTO();
        leadAssociationDTO1.setId(1L);
        LeadAssociationDTO leadAssociationDTO2 = new LeadAssociationDTO();
        assertThat(leadAssociationDTO1).isNotEqualTo(leadAssociationDTO2);
        leadAssociationDTO2.setId(leadAssociationDTO1.getId());
        assertThat(leadAssociationDTO1).isEqualTo(leadAssociationDTO2);
        leadAssociationDTO2.setId(2L);
        assertThat(leadAssociationDTO1).isNotEqualTo(leadAssociationDTO2);
        leadAssociationDTO1.setId(null);
        assertThat(leadAssociationDTO1).isNotEqualTo(leadAssociationDTO2);
    }
}
