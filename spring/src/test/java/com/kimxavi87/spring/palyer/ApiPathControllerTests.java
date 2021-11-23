package com.kimxavi87.spring.palyer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ApiPathControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenDuplicatePathsFromThwController_whenGet_thenOk() throws Exception {
        // api path가 겹쳐도 정확히 요청하는지 테스트
        mvc.perform(get("/api/path"))
                .andExpect(status().isOk())
                .andReturn();

        mvc.perform(get("/api/something"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void givenWrongPath_whenGetRequest_thenNotFound() throws Exception {
        mvc.perform(get("/api/path2"))
                .andExpect(status().isNotFound())
                .andReturn();
    }
}
