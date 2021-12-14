package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.controller.MemberController;
import com.kimxavi87.spring.player.dto.MemberInput;
import com.kimxavi87.spring.player.reposiotry.MemberRepository;
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
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = MemberController.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerTests {

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
    public void givenMemberInput_whenCreateMember_thenSuccess() throws Exception {
        MemberInput memberInput = MemberInput.builder()
                .name("park-ji-sung")
                .age(20)
                .mobilePhoneNumber("010-9999-9999")
                .build();

        MvcResult mvcResult = mvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapperUtil.objectToJsonString(memberInput).get()))
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void givenWrongAgeMember_whenCreateMember_thenFailed() throws Exception {
        MemberInput memberInput = MemberInput.builder()
                .name("park-ji-sung")
                .age(40)
                .mobilePhoneNumber("010-9999-9999")
                .build();

        System.out.println(memberInput.getAge());
        // 블로그랑 다르게, ConstraintViolationException 가 아니라 MethodArgumentNotValidException 가 발생
        // 상속 받은 부모 클래스에서 핸들링 하고 있음
        MvcResult mvcResult = mvc.perform(post("/member")
                .contentType(MediaType.APPLICATION_JSON)
                .content(ObjectMapperUtil.objectToJsonString(memberInput).get()))
                .andExpect(status().isBadRequest())
                .andReturn();
        
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void givenSmallerThanMinId_whenGetMemberById_thenBadRequest() throws Exception {
        mvc.perform(get("/member/4"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    public void givenWrongPageSize_whenRequestMemberList_thenOk() throws Exception {
        mvc.perform(get("/members?size=101"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    public void givenWrongPageSize_whenRequestMemberList_thenBadRequest() throws Exception {
        MvcResult mvcResult = mvc.perform(get("/membersWithPageValid?size=101"))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
