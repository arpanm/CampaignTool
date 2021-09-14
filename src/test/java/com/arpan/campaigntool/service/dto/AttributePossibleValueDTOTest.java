package com.arpan.campaigntool.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributePossibleValueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributePossibleValueDTO.class);
        AttributePossibleValueDTO attributePossibleValueDTO1 = new AttributePossibleValueDTO();
        attributePossibleValueDTO1.setId(1L);
        AttributePossibleValueDTO attributePossibleValueDTO2 = new AttributePossibleValueDTO();
        assertThat(attributePossibleValueDTO1).isNotEqualTo(attributePossibleValueDTO2);
        attributePossibleValueDTO2.setId(attributePossibleValueDTO1.getId());
        assertThat(attributePossibleValueDTO1).isEqualTo(attributePossibleValueDTO2);
        attributePossibleValueDTO2.setId(2L);
        assertThat(attributePossibleValueDTO1).isNotEqualTo(attributePossibleValueDTO2);
        attributePossibleValueDTO1.setId(null);
        assertThat(attributePossibleValueDTO1).isNotEqualTo(attributePossibleValueDTO2);
    }
}
