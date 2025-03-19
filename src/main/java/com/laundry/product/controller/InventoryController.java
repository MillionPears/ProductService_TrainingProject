package com.laundry.product.controller;

import com.laundry.product.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/inventories")
@RequiredArgsConstructor
public class InventoryController {
  private final InventoryService inventoryService;

//  @GetMapping("/check")
//  public Boolean checkInventory(@RequestParam UUID productId, @RequestParam int quantity) {
//    log.info("[INVENTORY CONTROLLER] - Received request to check stock: {}", productId);
//    return inventoryService.checkInventory(productId, quantity);
//  }

  @PostMapping("/reduce-stock")
  public void checkInventoryAndReduceStock(@RequestBody Map<UUID, Integer> productQuantities) {
    log.info("[INVENTORY CONTROLLER] - Received request to reduce stock: {}", productQuantities);
    inventoryService.checkInventoryAndReduceStock(productQuantities);
  }
}
