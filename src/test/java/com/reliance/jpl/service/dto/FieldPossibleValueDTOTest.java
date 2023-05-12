package com.reliance.jpl.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.jpl.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FieldPossibleValueDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldPossibleValueDTO.class);
        FieldPossibleValueDTO fieldPossibleValueDTO1 = new FieldPossibleValueDTO();
        fieldPossibleValueDTO1.setId(1L);
        FieldPossibleValueDTO fieldPossibleValueDTO2 = new FieldPossibleValueDTO();
        assertThat(fieldPossibleValueDTO1).isNotEqualTo(fieldPossibleValueDTO2);
        fieldPossibleValueDTO2.setId(fieldPossibleValueDTO1.getId());
        assertThat(fieldPossibleValueDTO1).isEqualTo(fieldPossibleValueDTO2);
        fieldPossibleValueDTO2.setId(2L);
        assertThat(fieldPossibleValueDTO1).isNotEqualTo(fieldPossibleValueDTO2);
        fieldPossibleValueDTO1.setId(null);
        assertThat(fieldPossibleValueDTO1).isNotEqualTo(fieldPossibleValueDTO2);
    }
}
