package com.reliance.jpl.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TelecallerInOutMapperTest {

    private TelecallerInOutMapper telecallerInOutMapper;

    @BeforeEach
    public void setUp() {
        telecallerInOutMapper = new TelecallerInOutMapperImpl();
    }
}
