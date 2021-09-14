package com.arpan.campaigntool.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeKeyDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeKeyDTO.class);
        AttributeKeyDTO attributeKeyDTO1 = new AttributeKeyDTO();
        attributeKeyDTO1.setId(1L);
        AttributeKeyDTO attributeKeyDTO2 = new AttributeKeyDTO();
        assertThat(attributeKeyDTO1).isNotEqualTo(attributeKeyDTO2);
        attributeKeyDTO2.setId(attributeKeyDTO1.getId());
        assertThat(attributeKeyDTO1).isEqualTo(attributeKeyDTO2);
        attributeKeyDTO2.setId(2L);
        assertThat(attributeKeyDTO1).isNotEqualTo(attributeKeyDTO2);
        attributeKeyDTO1.setId(null);
        assertThat(attributeKeyDTO1).isNotEqualTo(attributeKeyDTO2);
    }
}
