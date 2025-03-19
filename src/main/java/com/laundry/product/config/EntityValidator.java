package com.laundry.product.config;


import com.laundry.product.exception.CustomException;
import com.laundry.product.exception.ErrorCode;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Log4j2
public class EntityValidator {
  private final Validator validator;

  public EntityValidator(Validator validator) {
    this.validator = validator;
  }

  public <T> void validateEntity(T entity) {
    Set<ConstraintViolation<T>> violations = validator.validate(entity);
    if (!violations.isEmpty()) {
      List<String> errorMessages = violations.stream()
        .map(v -> v.getPropertyPath() + ": " + v.getMessage())
        .collect(Collectors.toList());
      log.error("[ENTITY VALIDATOR] - Validation failed for entity {} - Errors: {}",
        entity.getClass().getSimpleName(), errorMessages);
      throw new CustomException(ErrorCode.INVALID_FIELD, errorMessages);
    }
  }
}
