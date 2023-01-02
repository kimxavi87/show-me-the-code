package com.kimxavi87.normal.mapper;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.BeanPropertyDefinition;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.util.Json31;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ObjectMapperTests {

    @Test
    public void setter() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SampleObject sampleObject = new SampleObject(1, "hello");
        String json = objectMapper.writeValueAsString(sampleObject);
        System.out.println(json);

        ObjectMapper mapperJson31 = Json31.mapper();
        String json2 = mapperJson31.writeValueAsString(sampleObject);
        System.out.println(json2);

        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));


        AnnotatedType type = new AnnotatedType().type(SampleObject.class);
        JavaType javaType = objectMapper.constructType(type.getType());

        BeanDescription beanDescription = objectMapper.getSerializationConfig().introspect(javaType);
        List<BeanPropertyDefinition> properties = beanDescription.findProperties();
        for (BeanPropertyDefinition property : properties) {
            System.out.println(property.getName());
        }

    }

    @Setter
    @Getter
    @AllArgsConstructor
    public static class SampleObject {
        private int number;
        private String name;

        public void setData(String data) {
        }
    }
}
