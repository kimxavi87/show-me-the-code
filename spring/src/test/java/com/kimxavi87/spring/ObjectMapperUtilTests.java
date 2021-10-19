package com.kimxavi87.spring;

import com.kimxavi87.spring.player.dto.MemberInput;
import com.kimxavi87.spring.utils.ObjectMapperUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class ObjectMapperUtilTests {

    @Test
    public void objectToJsonString() {
        // getter 는 있어야 변환이 된다
        MemberInput memberInput = MemberInput.builder()
                .name("park-ji-sung")
                .age(40)
                .build();

        Optional<String> string = ObjectMapperUtil.objectToJsonString(memberInput);

        Assertions.assertTrue(string.isPresent());
    }
}
