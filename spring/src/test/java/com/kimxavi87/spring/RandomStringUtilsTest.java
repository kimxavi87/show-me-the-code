package com.kimxavi87.spring;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomStringUtilsTest {

    @Test
    public void testRandomString() {
        String randomString = RandomStringUtils.randomAlphabetic(2);
        assertThat(randomString.length()).isEqualTo(2);
    }
}
