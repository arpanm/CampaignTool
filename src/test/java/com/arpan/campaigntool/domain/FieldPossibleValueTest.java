package com.arpan.campaigntool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldPossibleValueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldPossibleValue.class);
        FieldPossibleValue fieldPossibleValue1 = new FieldPossibleValue();
        fieldPossibleValue1.setId(1L);
        FieldPossibleValue fieldPossibleValue2 = new FieldPossibleValue();
        fieldPossibleValue2.setId(fieldPossibleValue1.getId());
        assertThat(fieldPossibleValue1).isEqualTo(fieldPossibleValue2);
        fieldPossibleValue2.setId(2L);
        assertThat(fieldPossibleValue1).isNotEqualTo(fieldPossibleValue2);
        fieldPossibleValue1.setId(null);
        assertThat(fieldPossibleValue1).isNotEqualTo(fieldPossibleValue2);
    }
}
