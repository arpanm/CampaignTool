package com.reliance.jpl.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.jpl.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AttributePossibleValueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AttributePossibleValue.class);
        AttributePossibleValue attributePossibleValue1 = new AttributePossibleValue();
        attributePossibleValue1.setId(1L);
        AttributePossibleValue attributePossibleValue2 = new AttributePossibleValue();
        attributePossibleValue2.setId(attributePossibleValue1.getId());
        assertThat(attributePossibleValue1).isEqualTo(attributePossibleValue2);
        attributePossibleValue2.setId(2L);
        assertThat(attributePossibleValue1).isNotEqualTo(attributePossibleValue2);
        attributePossibleValue1.setId(null);
        assertThat(attributePossibleValue1).isNotEqualTo(attributePossibleValue2);
    }
}
