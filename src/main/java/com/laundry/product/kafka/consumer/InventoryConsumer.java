package com.laundry.product.kafka.consumer;

import com.laundry.product.event.PaymentEvent;
import com.laundry.product.parser.Parser;
import com.laundry.product.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Log4j2
public class InventoryConsumer {
  private final Parser parser;
  private final InventoryService inventoryService;
  @KafkaListener(topics = "#{T(com.laundry.product.enums.KafkaTopic).NOTIFY_INVENTORY_COMPENSATION_ACTION.getTopicName()}",
    groupId = "inventory-compensation-group")
  public void consumeInventoryCompensationEvent(String compensationEventReceived) {
    try {
      PaymentEvent compensationEvent = parser.parseToObject(compensationEventReceived, PaymentEvent.class);
      Map<UUID, Integer> productQuantities = compensationEvent.getProductQuantities();
      inventoryService.compensateInventory(productQuantities);
      log.info("[INVENTORY COMPENSATION] - Successfully compensated inventory for order [{}]",
        compensationEvent.getOrderId());
    } catch (Exception e) {
      log.error("[INVENTORY COMPENSATION] - Error compensating inventory: {}", e.getMessage(), e);
    }
  }

}
