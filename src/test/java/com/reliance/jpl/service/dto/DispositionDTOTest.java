package com.reliance.jpl.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.jpl.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DispositionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DispositionDTO.class);
        DispositionDTO dispositionDTO1 = new DispositionDTO();
        dispositionDTO1.setId(1L);
        DispositionDTO dispositionDTO2 = new DispositionDTO();
        assertThat(dispositionDTO1).isNotEqualTo(dispositionDTO2);
        dispositionDTO2.setId(dispositionDTO1.getId());
        assertThat(dispositionDTO1).isEqualTo(dispositionDTO2);
        dispositionDTO2.setId(2L);
        assertThat(dispositionDTO1).isNotEqualTo(dispositionDTO2);
        dispositionDTO1.setId(null);
        assertThat(dispositionDTO1).isNotEqualTo(dispositionDTO2);
    }
}
