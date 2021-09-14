package com.arpan.campaigntool.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TelecallerAssignmentMapperTest {

    private TelecallerAssignmentMapper telecallerAssignmentMapper;

    @BeforeEach
    public void setUp() {
        telecallerAssignmentMapper = new TelecallerAssignmentMapperImpl();
    }
}
