package com.laundry.product.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  NOT_FOUND(HttpStatus.NOT_FOUND, "{} with ID {} not found"),
  INVALID_FIELD(HttpStatus.BAD_REQUEST, "{} {}"),
  CONFLICT(HttpStatus.CONFLICT, "{} already exists: {}"),
  TIMEOUT(HttpStatus.REQUEST_TIMEOUT, "{} timeout after {}"),
  DATA_INTEGRITY_VIOLATION(HttpStatus.BAD_REQUEST, "Cannot {}: {}"),
  OPTIMISTIC_LOCK(HttpStatus.CONFLICT, "{} update failed: {}");

  private final HttpStatus status;
  private final String messageTemplate;

  ErrorCode(HttpStatus status, String messageTemplate) {
    this.status = status;
    this.messageTemplate = messageTemplate;
  }

  public String formatMessage(Object... args) {
    return String.format(messageTemplate.replace("{}", "%s"), args);
  }
}

