package com.arpan.campaigntool.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelecallerInOutDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelecallerInOutDTO.class);
        TelecallerInOutDTO telecallerInOutDTO1 = new TelecallerInOutDTO();
        telecallerInOutDTO1.setId(1L);
        TelecallerInOutDTO telecallerInOutDTO2 = new TelecallerInOutDTO();
        assertThat(telecallerInOutDTO1).isNotEqualTo(telecallerInOutDTO2);
        telecallerInOutDTO2.setId(telecallerInOutDTO1.getId());
        assertThat(telecallerInOutDTO1).isEqualTo(telecallerInOutDTO2);
        telecallerInOutDTO2.setId(2L);
        assertThat(telecallerInOutDTO1).isNotEqualTo(telecallerInOutDTO2);
        telecallerInOutDTO1.setId(null);
        assertThat(telecallerInOutDTO1).isNotEqualTo(telecallerInOutDTO2);
    }
}
