package com.reliance.jpl.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FieldPossibleValueMapperTest {

    private FieldPossibleValueMapper fieldPossibleValueMapper;

    @BeforeEach
    public void setUp() {
        fieldPossibleValueMapper = new FieldPossibleValueMapperImpl();
    }
}
