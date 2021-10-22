package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;
import org.springframework.util.StringUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class StringUtilsTests {

    @Test
    public void givenCommaString_whenSplit_thenDoSuccess() {
        String stringWithComma = "abc, ddd, ccc";
        // trim 은 해주지 않음
        String[] strings = StringUtils.commaDelimitedListToStringArray(stringWithComma);

        assertThat(StringUtils.trimWhitespace(strings[0])).isEqualTo("abc");
        assertThat(StringUtils.trimWhitespace(strings[1])).isEqualTo("ddd");
        assertThat(StringUtils.trimWhitespace(strings[2])).isEqualTo("ccc");
    }
}
