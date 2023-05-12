package com.reliance.jpl.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DispositionSubmissionValueMapperTest {

    private DispositionSubmissionValueMapper dispositionSubmissionValueMapper;

    @BeforeEach
    public void setUp() {
        dispositionSubmissionValueMapper = new DispositionSubmissionValueMapperImpl();
    }
}
