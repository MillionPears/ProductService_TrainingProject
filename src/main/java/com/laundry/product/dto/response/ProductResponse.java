package com.laundry.product.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

  private UUID id;

  private String name;

  private String description;

  private BigDecimal price;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
}
