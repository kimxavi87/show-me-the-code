package com.kimxavi87.spring.palyer;

import com.kimxavi87.spring.player.PlayerService;
import com.kimxavi87.spring.player.dto.MemberInput;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class PlayerServiceTests {

    @Autowired
    private PlayerService service;

    @Test
    public void whenInputIsWrong_thenThrowException() {
        // JUnit5 : 예외 발생 테스트
        // Service Layer, Parameter Valid 테스트
        assertThrows(ConstraintViolationException.class, () -> {
            service.dummy(new MemberInput("park-ji-sung", 35, "010-9999-9999"));
        });
    }
}
