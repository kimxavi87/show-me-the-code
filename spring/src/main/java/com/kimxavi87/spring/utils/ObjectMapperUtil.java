package com.kimxavi87.spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE
            = new TypeReference<Map<String, Object>>() {};

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
}
