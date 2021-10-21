package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.MemberController;
import com.kimxavi87.spring.player.dto.MemberInput;
import com.kimxavi87.spring.player.reposiotry.MemberRepository;
import com.kimxavi87.spring.utils.ObjectMapperUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MemberController.class)
public class PlayerControllerTests {

    @Autowired
    private MockMvc mvc;

    @MockBean
    MemberRepository memberRepository;

    @Test
    public void whenGetMember_thenSuccess() throws Exception {
        // MockMvcRequestBuilders.get()
        mvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void whenCreateMember_givenWrongAgeMember_thenFailed() throws Exception {
        MemberInput memberInput = MemberInput.builder()
                .name("park-ji-sung")
                .age(40)
                .build();

        System.out.println(memberInput.getAge());
        mvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapperUtil.objectToJsonString(memberInput).get()))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void whenGetMemberById_givenSmallerThanMinId_thenBadRequest() throws Exception {
        mvc.perform(get("/member/4"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }
}
