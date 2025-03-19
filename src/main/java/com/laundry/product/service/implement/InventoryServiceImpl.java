package com.laundry.product.service.implement;

import com.laundry.product.config.EntityValidator;
import com.laundry.product.entity.Inventory;
import com.laundry.product.exception.CustomException;
import com.laundry.product.exception.ErrorCode;
import com.laundry.product.repository.InventoryRepository;
import com.laundry.product.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

  private final InventoryRepository inventoryRepository;
  private final EntityValidator validator;

  @Override
  @Transactional
  public void createInventory(UUID productId) {
    Inventory inventory = Inventory.builder().productId(productId).reservedQuantity(0).availableQuantity(10).build();
    //validator.validateEntity(inventory);
    inventoryRepository.save(inventory);

  }

  @Override
  @Transactional
  public void checkInventoryAndReduceStock(Map<UUID, Integer> productQuantities) {
    List<UUID> productIds = new ArrayList<>(productQuantities.keySet());
    List<Inventory> inventories = inventoryRepository.findAllByProductIdIn(productIds);
    log.debug("[INVENTORY SERVICE] - Fetched [{}] inventories from DB", inventories.size());
    Map<UUID, Inventory> inventoryMap = inventories.stream().collect(Collectors.toMap(Inventory::getProductId, Function.identity()));
    for (UUID productId : productQuantities.keySet()) {
      Inventory inventory = inventoryMap.get(productId);
      if (inventory.getAvailableQuantity() < productQuantities.get(productId))
        throw new CustomException(ErrorCode.PRODUCT_OUT_OF_STOCK, productId);
      int newStock = inventory.getAvailableQuantity() - productQuantities.get(productId);
      log.debug("[INVENTORY SERVICE] - Reducing stock for product [{}]: Old Quantity: [{}], New Quantity: [{}]", productId, inventory.getAvailableQuantity(), newStock);
      inventory.setReservedQuantity(productQuantities.get(productId));
      inventory.setAvailableQuantity(newStock);
      validator.validateEntity(inventory);
    }
    inventoryRepository.saveAll(inventories);
    log.info("[INVENTORY SERVICE] - Successfully updated inventory for [{}] products", inventories.size());

  }

  @Transactional
  @Override
  public void compensateInventory(Map<UUID, Integer> productQuantities) {
    List<UUID> productIds = new ArrayList<>(productQuantities.keySet());
    List<Inventory> inventories = inventoryRepository.findAllByProductIdIn(productIds);
    log.debug("[INVENTORY SERVICE] - Fetched [{}] inventories for compensation", inventories.size());
    Map<UUID, Inventory> inventoryMap = inventories.stream()
      .collect(Collectors.toMap(Inventory::getProductId, Function.identity()));
    for (UUID productId : productQuantities.keySet()) {
      Inventory inventory = inventoryMap.get(productId);
      int quantityToRestore = productQuantities.get(productId);
      int newStock = inventory.getAvailableQuantity() + quantityToRestore;
      log.debug("[INVENTORY SERVICE] - Restoring stock for product [{}]: Old Quantity: [{}], New Quantity: [{}]",
        productId, inventory.getAvailableQuantity(), newStock);
      inventory.setReservedQuantity(inventory.getReservedQuantity() - quantityToRestore);
      inventory.setAvailableQuantity(newStock);
      validator.validateEntity(inventory);
    }
    inventoryRepository.saveAll(inventories);
    log.info("[INVENTORY SERVICE] - Successfully compensated inventory for [{}] products", inventories.size());
  }
}
