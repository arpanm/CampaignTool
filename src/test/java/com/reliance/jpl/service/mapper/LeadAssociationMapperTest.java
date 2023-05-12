package com.reliance.jpl.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeadAssociationMapperTest {

    private LeadAssociationMapper leadAssociationMapper;

    @BeforeEach
    public void setUp() {
        leadAssociationMapper = new LeadAssociationMapperImpl();
    }
}
