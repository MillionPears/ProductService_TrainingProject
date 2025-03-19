package com.laundry.product.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  // Product Service (20)
  PRODUCT_NOT_FOUND(20001, HttpStatus.NOT_FOUND, "Product with ID {} not found"),
  PRODUCT_OUT_OF_STOCK(10002, HttpStatus.NOT_FOUND, "Product with ID {} has been sold out"),
  CHECK_INVENTORY_FAIL(10003, HttpStatus.CONFLICT, "Check Inventory failed"),
  PRODUCT_NAME_ALREADY_EXISTS(10004, HttpStatus.CONFLICT, "Product with name: {} already exists"),
  INVALID_FIELD(10005, HttpStatus.BAD_REQUEST, "Invalid field"),
  OPTIMISTIC_LOCK(10006,HttpStatus.CONFLICT ,"OptimisticLock occurred" );


  private final int code;
  private final HttpStatus status;
  private final String messageTemplate;

  ErrorCode(int code, HttpStatus status, String messageTemplate) {
    this.code=code;
    this.status = status;
    this.messageTemplate = messageTemplate;
  }

  public String formatMessage(Object... args) {
    return String.format(messageTemplate.replace("{}", "%s"), args);
  }
}

