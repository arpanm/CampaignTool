package com.arpan.campaigntool.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LeadUploadFileMapperTest {

    private LeadUploadFileMapper leadUploadFileMapper;

    @BeforeEach
    public void setUp() {
        leadUploadFileMapper = new LeadUploadFileMapperImpl();
    }
}
