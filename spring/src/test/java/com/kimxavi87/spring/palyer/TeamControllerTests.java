package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.dto.TeamInput;
import com.kimxavi87.spring.utils.ObjectMapperUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// 통합테스트할 때는 아래와 같이 @SpringBootTest, @AutoConfigureMockMvc
// @MockMvcTest 는 Controller만 테스트해줄 때 사용 (내부 bean들이 mocking 됨)
@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void givenValidatorHasError_whenCreateTeam_thenResponse400() throws Exception {
        TeamInput input = new TeamInput("", 1000);
        mvc.perform(post("/team").contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapperUtil.objectToJsonString(input).get()))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
