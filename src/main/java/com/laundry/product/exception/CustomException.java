package com.laundry.product.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {
  private final ErrorCode errorCode;
  private final HttpStatus status;
  private final String errorMessage;

  public CustomException(ErrorCode errorCode, Object... args) {
    super(errorCode.formatMessage(args));
    this.errorCode = errorCode;
    this.status = errorCode.getStatus();
    this.errorMessage = errorCode.formatMessage(args);
  }
}
