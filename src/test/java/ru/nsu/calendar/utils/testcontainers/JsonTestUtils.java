package ru.nsu.calendar.utils.testcontainers;

import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTestUtils {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public static<T> T fromJson(String json, Class<T> jsonClass){
        try {
            return objectMapper.readValue(json, jsonClass);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
