package com.laundry.product.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse<T> {
  private T data;
  private OffsetDateTime timestamp;

  public ApiResponse(T data) {
    this.data = data;
    this.timestamp = OffsetDateTime.now();
  }
}
