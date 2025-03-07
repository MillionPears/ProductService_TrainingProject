package com.laundry.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
  private String errorCode;
  private HttpStatus status;
  private String message;
  private List<FieldError> fieldErrors;
  private OffsetDateTime timestamp;

  @Getter
  @Builder
  public static class FieldError {
    private String field;
    private String message;
  }

  public static ErrorResponse of(String errorCode, HttpStatus status, String message, List<FieldError> fieldErrors) {
    return ErrorResponse.builder()
      .errorCode(errorCode)
      .status(status)
      .message(message)
      .fieldErrors(fieldErrors)
      .timestamp(OffsetDateTime.now())
      .build();
  }
}
