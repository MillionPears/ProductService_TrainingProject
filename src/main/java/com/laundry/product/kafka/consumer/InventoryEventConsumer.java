package com.laundry.product.kafka.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.laundry.product.entity.Inventory;
import com.laundry.product.event.InventoryEvent;
import com.laundry.product.parser.EventParser;
import com.laundry.product.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Log4j2
public class InventoryEventConsumer {
  private final InventoryService inventoryService;
  private final EventParser eventParser;

  @KafkaListener(topics = "${kafka.topics.order-inventory}", groupId = "order-inventory-group")
  public void consumeOrderInventoryEvent(String inventoryReceived){
    InventoryEvent inventoryEvent = eventParser.parseEvent(inventoryReceived,InventoryEvent.class);
    Map<UUID, Integer> productQuantities = inventoryEvent.getProducts().stream()
      .collect(Collectors.toMap(InventoryEvent.ProductQuantity::getProductId, InventoryEvent.ProductQuantity::getQuantity));
    log.info("[InventoryConsumer: {}]", inventoryEvent);
    try {
      inventoryService.reduceStock(productQuantities);
      log.info(" [INVENTORY EVENT CONSUMER] - Successfully updated inventory for order");
    } catch (Exception e) {
      log.error(" [INVENTORY EVENT CONSUMER] - Error updating inventory: {}", e.getMessage(), e);
    }
  }



}
