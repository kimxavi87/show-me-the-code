package com.kimxavi87.spring.utils;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<>() {};

    public static Optional<String> objectToJsonString(Object input) {
        try {
            return Optional.of(objectMapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            log.error("error", e);
            return Optional.empty();
        }
    }

    public static Map<String, Object> objectToMap(Object object) {
        // TypeReference is Thread-Safe
        // https://groups.google.com/g/jackson-user/c/fB0UhzdSivQ?pli=1
        return objectMapper.convertValue(object, MAP_TYPE_REFERENCE);
    }

    @Nullable
    public static <T> T jsonToObject(String json, Class<T> clazz) {
        if (!StringUtils.hasText(json)) {
            return null;
        }

        try {
            return clazz.cast(objectMapper.readValue(json, clazz));
        } catch (IOException e) {
            log.error("error", e);
            return null;
        }
    }

    public static <T> Optional<T> jsonToObject(String json, TypeReference<T> typeReference) {
        if (!StringUtils.hasText(json)) {
            return Optional.empty();
        }

        try {
            // NoArgsConstructor 가 필요함
            return Optional.of(objectMapper.readValue(json, typeReference));
        } catch (IOException e) {
            log.error("error", e);
            return Optional.empty();
        }
    }

    public static Map<String, Object> jsonToMap(String json) {
        Map<String, Object> map = new HashMap<>();
        try {
            map = objectMapper.readValue(json, MAP_TYPE_REFERENCE);
        } catch (IOException e) {
            log.error("error", e);
        }

        return map;
    }
}
