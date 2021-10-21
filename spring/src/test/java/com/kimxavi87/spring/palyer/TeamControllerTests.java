package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.TeamController;
import com.kimxavi87.spring.player.dto.TeamInput;
import com.kimxavi87.spring.utils.ObjectMapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = TeamController.class)
public class TeamControllerTests {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenCreateTeam_givenValidatorHasError_thenResponse400() throws Exception {
        TeamInput input = new TeamInput("liverpool", 1000);
        Optional<String> optionalS = ObjectMapperUtil.objectToJsonString(input);
        mvc.perform(post("/team").contentType(MediaType.APPLICATION_JSON)
                .content(optionalS.get()))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
