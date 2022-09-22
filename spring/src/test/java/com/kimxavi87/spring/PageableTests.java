package com.kimxavi87.spring;

import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;

import static org.assertj.core.api.Assertions.assertThat;

public class PageableTests {

    @Test
    public void offset() {
        PageRequest of = PageRequest.of(2, 23);

        System.out.println(of.getOffset());
        assertThat(of.getOffset()).isEqualTo(46);
    }
}
