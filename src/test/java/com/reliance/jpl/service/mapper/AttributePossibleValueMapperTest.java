package com.reliance.jpl.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributePossibleValueMapperTest {

    private AttributePossibleValueMapper attributePossibleValueMapper;

    @BeforeEach
    public void setUp() {
        attributePossibleValueMapper = new AttributePossibleValueMapperImpl();
    }
}
