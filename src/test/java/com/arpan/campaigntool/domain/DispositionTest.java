package com.arpan.campaigntool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DispositionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disposition.class);
        Disposition disposition1 = new Disposition();
        disposition1.setId(1L);
        Disposition disposition2 = new Disposition();
        disposition2.setId(disposition1.getId());
        assertThat(disposition1).isEqualTo(disposition2);
        disposition2.setId(2L);
        assertThat(disposition1).isNotEqualTo(disposition2);
        disposition1.setId(null);
        assertThat(disposition1).isNotEqualTo(disposition2);
    }
}
