package com.arpan.campaigntool.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CallDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CallDTO.class);
        CallDTO callDTO1 = new CallDTO();
        callDTO1.setId(1L);
        CallDTO callDTO2 = new CallDTO();
        assertThat(callDTO1).isNotEqualTo(callDTO2);
        callDTO2.setId(callDTO1.getId());
        assertThat(callDTO1).isEqualTo(callDTO2);
        callDTO2.setId(2L);
        assertThat(callDTO1).isNotEqualTo(callDTO2);
        callDTO1.setId(null);
        assertThat(callDTO1).isNotEqualTo(callDTO2);
    }
}
