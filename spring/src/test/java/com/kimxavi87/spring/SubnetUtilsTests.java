package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SubnetUtilsTests {
    private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
    // 0 ~ 32
    private static final String SLASH_FORMAT = IP_ADDRESS + "/(\\d{1,2})";

    private static final Pattern CIDR_PATTERN = Pattern.compile(SLASH_FORMAT);

    @Test
    public void givenCidr_whenCheckValidate_thenIsValid() {
        String cidr = "1.2.3.4/22";
        assertTrue(isValidCidr(cidr));
    }

    @Test
    public void givenWrongCidr_whenCheckValidate_thenIsInvalid() {
        String cidr = "256.2.3.4/22";
        assertFalse(isValidCidr(cidr));

        String cidr2 = "1.256.3.4/22";
        assertFalse(isValidCidr(cidr2));

        String cidr3 = "1.1.256.4/22";
        assertFalse(isValidCidr(cidr3));

        String cidr4 = "1.1.1.256/22";
        assertFalse(isValidCidr(cidr4));

        String cidr5 = "1.1.1.1/33";
        assertFalse(isValidCidr(cidr5));
    }

    private boolean isValidCidr(String cidr) {

        Matcher matcher = CIDR_PATTERN.matcher(cidr);
        if (matcher.matches()) {
            // matcher.group(0) 에는 전체 스트링이 들어감
            System.out.println(matcher.group(0));
            // 1 부터 체크
            for (int i = 1; i <= 4; i++) {
                System.out.println(String.format("Check %s ", matcher.group(i)));
                if (Integer.parseInt(matcher.group(i)) <= 0 || Integer.parseInt(matcher.group(i)) > 255) {
                    return false;
                }
            }

            System.out.println(String.format("Check %s ", matcher.group(5)));
            if (Integer.parseInt(matcher.group(5)) <= 0 || Integer.parseInt(matcher.group(5)) > 32) {
                return false;
            }
        }

        return true;
    }
}
