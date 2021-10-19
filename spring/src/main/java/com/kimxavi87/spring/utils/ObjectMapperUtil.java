package com.kimxavi87.spring.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperUtil {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static Optional<String> objectToJsonString(Object input) {
        try {
            return Optional.of(objectMapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            log.error("error", e);
            return Optional.empty();
        }
    }
}
