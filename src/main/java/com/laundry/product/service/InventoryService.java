package com.laundry.product.service;


import java.util.Map;
import java.util.UUID;

public interface InventoryService {
  void createInventory(UUID productId);
//  void checkInventory(UUID productId, int quantity);
  void reduceStock(Map<UUID, Integer> productQuantities);
}
