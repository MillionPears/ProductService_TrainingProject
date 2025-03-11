package com.laundry.product.parser;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class EventParser {
  private final ObjectMapper objectMapper;

  public <T> T parseEvent(String eventJson, Class<T> eventType) {
    try {
      return objectMapper.readValue(eventJson, eventType);
    } catch (JsonProcessingException e) {
      log.error("JsonProcessingException while parsing event to {}: {}", eventType.getSimpleName(), e.getMessage());
      throw new RuntimeException("Invalid event data for " + eventType.getSimpleName());
    }
  }
}
