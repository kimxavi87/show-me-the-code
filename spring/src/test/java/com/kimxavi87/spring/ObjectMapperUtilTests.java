package com.kimxavi87.spring;

import com.fasterxml.jackson.core.type.TypeReference;
import com.kimxavi87.spring.player.dto.MemberInput;
import com.kimxavi87.spring.utils.ObjectMapperUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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

    @Test
    public void givenGetterAllArgsConst_whenObjectToJson_thenSuccess() {
        GetterAllArgsConObject hhh = new GetterAllArgsConObject("hhh", 10);
        Optional<String> jsonString = ObjectMapperUtil.objectToJsonString(hhh);
        System.out.println(jsonString.get());
        assertThat(jsonString.get()).isEqualTo("{\"name\":\"hhh\",\"age\":10}");
    }

    @Test
    public void givenGetterBuilder_whenObjectToJson_thenSuccess() {
        GetterBuilderObject kim = new GetterBuilderObject("hhh", 10);
        Optional<String> jsonString = ObjectMapperUtil.objectToJsonString(kim);
        System.out.println(jsonString.get());
        assertThat(jsonString.get()).isEqualTo("{\"name\":\"hhh\",\"age\":10}");
    }

    @Test
    public void givenObject_whenConvert_thenReturnMap() {
        String name = "kim";
        int age = 30;
        TestObject kim = new TestObject(name, age);
        Map<String, Object> result = ObjectMapperUtil.objectToMap(kim);
        assertThat(result.get("name")).isEqualTo(name);
        assertThat(result.get("age")).isEqualTo(age);
    }

    @Test
    public void givenJsonString_whenConvertObject_thenSuccess() {
        TestObject testObject = new TestObject("a", 20);
        Optional<String> jsonString = ObjectMapperUtil.objectToJsonString(testObject);
        Optional<TestObject> result= ObjectMapperUtil.jsonToObject(jsonString.get(), new TypeReference<>() {});

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getName()).isEqualTo(testObject.getName());
        assertThat(result.get().getAge()).isEqualTo(testObject.getAge());
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TestObject {
        private String name;
        private int age;
    }

    @Getter
    @AllArgsConstructor
    public static class GetterAllArgsConObject {
        private String name;
        private int age;
    }

    @Getter
    @Builder
    public static class GetterBuilderObject {
        private String name;
        private int age;
    }
}
