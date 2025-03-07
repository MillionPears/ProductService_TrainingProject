package com.laundry.product.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCreateRequest {
  @NotNull
  @Column(unique = true)
  private String name;

  @NotNull
  private String description;

  @NotNull
  @Min(0)
  private BigDecimal price;
}
