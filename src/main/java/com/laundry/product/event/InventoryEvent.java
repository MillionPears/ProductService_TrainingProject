package com.laundry.product.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InventoryEvent {
  private Long orderId;
  private List<ProductQuantity> products;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ProductQuantity {
    private UUID productId;
    private int quantity;
  }
}
