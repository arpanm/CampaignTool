package com.reliance.jpl.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributeKeyMapperTest {

    private AttributeKeyMapper attributeKeyMapper;

    @BeforeEach
    public void setUp() {
        attributeKeyMapper = new AttributeKeyMapperImpl();
    }
}
