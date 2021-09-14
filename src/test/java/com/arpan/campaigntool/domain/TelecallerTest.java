package com.arpan.campaigntool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelecallerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telecaller.class);
        Telecaller telecaller1 = new Telecaller();
        telecaller1.setId(1L);
        Telecaller telecaller2 = new Telecaller();
        telecaller2.setId(telecaller1.getId());
        assertThat(telecaller1).isEqualTo(telecaller2);
        telecaller2.setId(2L);
        assertThat(telecaller1).isNotEqualTo(telecaller2);
        telecaller1.setId(null);
        assertThat(telecaller1).isNotEqualTo(telecaller2);
    }
}
