package com.arpan.campaigntool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeadAssociationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(LeadAssociation.class);
        LeadAssociation leadAssociation1 = new LeadAssociation();
        leadAssociation1.setId(1L);
        LeadAssociation leadAssociation2 = new LeadAssociation();
        leadAssociation2.setId(leadAssociation1.getId());
        assertThat(leadAssociation1).isEqualTo(leadAssociation2);
        leadAssociation2.setId(2L);
        assertThat(leadAssociation1).isNotEqualTo(leadAssociation2);
        leadAssociation1.setId(null);
        assertThat(leadAssociation1).isNotEqualTo(leadAssociation2);
    }
}
