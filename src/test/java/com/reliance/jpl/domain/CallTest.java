package com.reliance.jpl.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.reliance.jpl.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CallTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Call.class);
        Call call1 = new Call();
        call1.setId(1L);
        Call call2 = new Call();
        call2.setId(call1.getId());
        assertThat(call1).isEqualTo(call2);
        call2.setId(2L);
        assertThat(call1).isNotEqualTo(call2);
        call1.setId(null);
        assertThat(call1).isNotEqualTo(call2);
    }
}
