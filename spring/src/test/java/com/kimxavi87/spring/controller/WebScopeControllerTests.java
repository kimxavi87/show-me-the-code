package com.kimxavi87.spring.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WebScopeControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenSessionScopeComponent_whenHttpRequest_thenValueIsDifferentPerRequest() throws Exception {
        // proxyMode = ScopedProxyMode.TARGET_CLASS 를 하지 않으면 Exception 발생
        // SCOPE_REQUEST 하면 객체가 계속 바뀜
        String firstResponse = mvc.perform(get("/scope"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        String secondResponse = mvc.perform(get("/scope"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(firstResponse)
                .isNotEqualTo(secondResponse);
    }
}
