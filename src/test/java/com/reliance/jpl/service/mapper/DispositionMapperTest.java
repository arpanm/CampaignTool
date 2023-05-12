package com.reliance.jpl.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DispositionMapperTest {

    private DispositionMapper dispositionMapper;

    @BeforeEach
    public void setUp() {
        dispositionMapper = new DispositionMapperImpl();
    }
}
