package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.TeamController;
import com.kimxavi87.spring.player.TeamInputValidator;
import com.kimxavi87.spring.player.dto.TeamInput;
import com.kimxavi87.spring.utils.ObjectMapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TeamControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenCreateTeam_givenValidatorHasError_thenResponse400() throws Exception {
        TeamInput input = new TeamInput("", 1000);
        mvc.perform(post("/team").contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapperUtil.objectToJsonString(input).get()))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
