package com.reliance.jpl.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.jpl.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelecallerDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelecallerDTO.class);
        TelecallerDTO telecallerDTO1 = new TelecallerDTO();
        telecallerDTO1.setId(1L);
        TelecallerDTO telecallerDTO2 = new TelecallerDTO();
        assertThat(telecallerDTO1).isNotEqualTo(telecallerDTO2);
        telecallerDTO2.setId(telecallerDTO1.getId());
        assertThat(telecallerDTO1).isEqualTo(telecallerDTO2);
        telecallerDTO2.setId(2L);
        assertThat(telecallerDTO1).isNotEqualTo(telecallerDTO2);
        telecallerDTO1.setId(null);
        assertThat(telecallerDTO1).isNotEqualTo(telecallerDTO2);
    }
}
