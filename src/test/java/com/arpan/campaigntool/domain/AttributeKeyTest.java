package com.arpan.campaigntool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributeKeyTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributeKey.class);
        AttributeKey attributeKey1 = new AttributeKey();
        attributeKey1.setId(1L);
        AttributeKey attributeKey2 = new AttributeKey();
        attributeKey2.setId(attributeKey1.getId());
        assertThat(attributeKey1).isEqualTo(attributeKey2);
        attributeKey2.setId(2L);
        assertThat(attributeKey1).isNotEqualTo(attributeKey2);
        attributeKey1.setId(null);
        assertThat(attributeKey1).isNotEqualTo(attributeKey2);
    }
}
