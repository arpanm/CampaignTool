package com.arpan.campaigntool.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DispositionSubmissionMapperTest {

    private DispositionSubmissionMapper dispositionSubmissionMapper;

    @BeforeEach
    public void setUp() {
        dispositionSubmissionMapper = new DispositionSubmissionMapperImpl();
    }
}
