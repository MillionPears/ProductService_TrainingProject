package com.laundry.product.dto.request;

import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductUpdateRequest {
  private String name;

  private String description;

  private BigDecimal price;

  private Integer stockQuantity;

  private String category;
}
