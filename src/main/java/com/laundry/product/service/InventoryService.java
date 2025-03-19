package com.laundry.product.service;


import java.util.Map;
import java.util.UUID;

public interface InventoryService {
  void createInventory(UUID productId);
//  boolean checkInventory(UUID productId, int quantity);
  void checkInventoryAndReduceStock(Map<UUID, Integer> productQuantities);
  void compensateInventory(Map<UUID, Integer> productQuantities);
}
