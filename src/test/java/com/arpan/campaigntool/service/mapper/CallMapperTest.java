package com.arpan.campaigntool.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CallMapperTest {

    private CallMapper callMapper;

    @BeforeEach
    public void setUp() {
        callMapper = new CallMapperImpl();
    }
}
