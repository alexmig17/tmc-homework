package com.epam;

import static junit.framework.TestCase.assertTrue;

import org.junit.jupiter.api.Test;

class TestDriverDevelopmentAppTest {

    @Test
    void should_runMainWithoutExceptions_when_noArgs() {
        TestDriverDevelopmentApp.main(null);
        assertTrue(true);
    }
}