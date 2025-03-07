package com.laundry.product.dto.response;

import com.laundry.order.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@Builder
public class InventoryResponse {
  private UUID id ;
  private UUID productId;
  private Integer availableQuantity;
  private Integer reservedQuantity ;
  private Integer totalQuantity;
  private LocalDateTime createdDate;
  private LocalDateTime lastModifiedDate;
}
