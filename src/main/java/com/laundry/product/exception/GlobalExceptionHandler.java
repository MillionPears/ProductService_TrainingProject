package com.laundry.product.exception;

import com.laundry.product.dto.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.List;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

  @ExceptionHandler(CustomException.class)
  public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
    log.error("[EXCEPTION] - CustomException: {} | Code: {}", ex.getErrorMessage(), ex.getErrorCode().getStatus().value());
    ErrorResponse response = ErrorResponse.of(
      String.valueOf(ex.getErrorCode().getStatus().value()),
      ex.getStatus(),
      ex.getErrorMessage(),
      null
    );
    return ResponseEntity.status(ex.getStatus()).body(response);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
    List<ErrorResponse.FieldError> errors = ex.getBindingResult().getFieldErrors()
      .stream()
      .map(fieldError -> ErrorResponse.FieldError.builder()
        .field(fieldError.getField())
        .message(fieldError.getDefaultMessage())
        .build())
      .toList();
    log.warn("[EXCEPTION] - Validation failed: {}", errors);
    ErrorResponse response = ErrorResponse.of(
      String.valueOf(ex.getBody().getStatus()),
      HttpStatus.valueOf(ex.getStatusCode().value()),
      "Validation failed",
      errors
    );
    return ResponseEntity.status(ex.getStatusCode()).body(response);
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ErrorResponse> handleMissingParams(MissingServletRequestParameterException ex) {
    log.warn("[EXCEPTION] - Missing parameter: {}", ex.getParameterName());
    ErrorResponse response = ErrorResponse.of(
      "400",
      HttpStatus.BAD_REQUEST,
      "Missing required parameter: " + ex.getParameterName(),
      null
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
    ErrorResponse response = ErrorResponse.of(
      "400",
      HttpStatus.BAD_REQUEST,
      "Invalid parameter: " + ex.getName() + " should be of type " + ex.getRequiredType().getSimpleName(),
      null
    );
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

}
