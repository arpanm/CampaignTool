package com.arpan.campaigntool.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TelecallerMapperTest {

    private TelecallerMapper telecallerMapper;

    @BeforeEach
    public void setUp() {
        telecallerMapper = new TelecallerMapperImpl();
    }
}
