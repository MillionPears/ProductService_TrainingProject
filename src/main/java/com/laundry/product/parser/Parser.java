package com.laundry.product.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Parser {
  private final ObjectMapper objectMapper;

  public String parseToJon(Object event) {
    try {
      return new ObjectMapper().writeValueAsString(event);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Error serializing event to JSON", e);
    }
  }

  public <T> T parseToObject(String eventJson, Class<T> eventType) {
    try {
      return objectMapper.readValue(eventJson, eventType);
    } catch (JsonProcessingException e) {
      throw new RuntimeException("Invalid event data for " + eventType.getSimpleName());
    }
  }
}
