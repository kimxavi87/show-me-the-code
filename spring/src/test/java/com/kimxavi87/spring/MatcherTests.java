package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

public class MatcherTests {
    // https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
    public static final Pattern PATTERN_INTEGER_OR_ALPHABET = Pattern.compile("[0-9|a-z|A-Z]*");

    @Test
    public void givenString_whenPatternMatch_thenHaveWrongCharacter() {
        String matchString = "12093jnaskfjlksj2nd38";
        Matcher matcher = PATTERN_INTEGER_OR_ALPHABET.matcher(matchString);
        assertThat(matcher.matches()).isEqualTo(true);
    }

    @Test
    public void givenNotMatchString_whenMatches_thenFalse() {
        String notMatchString = "12093jnaskfjlksj2nd38@*(#*$";
        Matcher matcher = PATTERN_INTEGER_OR_ALPHABET.matcher(notMatchString);

        assertThat(matcher.matches()).isEqualTo(false);
    }

    // todo string pattern parsing
}
