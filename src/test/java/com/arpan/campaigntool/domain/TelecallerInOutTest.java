package com.arpan.campaigntool.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.arpan.campaigntool.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelecallerInOutTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TelecallerInOut.class);
        TelecallerInOut telecallerInOut1 = new TelecallerInOut();
        telecallerInOut1.setId(1L);
        TelecallerInOut telecallerInOut2 = new TelecallerInOut();
        telecallerInOut2.setId(telecallerInOut1.getId());
        assertThat(telecallerInOut1).isEqualTo(telecallerInOut2);
        telecallerInOut2.setId(2L);
        assertThat(telecallerInOut1).isNotEqualTo(telecallerInOut2);
        telecallerInOut1.setId(null);
        assertThat(telecallerInOut1).isNotEqualTo(telecallerInOut2);
    }
}
