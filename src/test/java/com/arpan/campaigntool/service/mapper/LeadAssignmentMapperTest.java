package com.arpan.campaigntool.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeadAssignmentMapperTest {

    private LeadAssignmentMapper leadAssignmentMapper;

    @BeforeEach
    public void setUp() {
        leadAssignmentMapper = new LeadAssignmentMapperImpl();
    }
}
