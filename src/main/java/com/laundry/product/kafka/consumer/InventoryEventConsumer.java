package com.laundry.product.kafka.consumer;

import com.laundry.product.enums.InventoryProcessStatus;
import com.laundry.product.enums.KafkaTopic;
import com.laundry.product.event.InventoryEvent;
import com.laundry.product.parser.Parser;
import com.laundry.product.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Log4j2
public class InventoryEventConsumer {
  private final InventoryService inventoryService;
  private final Parser eventParser;
  private final KafkaTemplate<String, String> stringKafkaTemplate;

  private final String inventoryStatusTopic = KafkaTopic.INVENTORY_REDUCE_STOCK_STATUS.getTopicName();

  @KafkaListener(topics = "#{T(com.laundry.product.enums.KafkaTopic).INVENTORY_REDUCE_STOCK_EVENT.getTopicName()}", groupId = "order-inventory-group")
  public void consumeOrderInventoryEvent(String inventoryReceived, @Header(KafkaHeaders.RECEIVED_KEY) String eventId) {
    InventoryEvent inventoryEvent = eventParser.parseToObject(inventoryReceived, InventoryEvent.class);
    log.info("[InventoryConsumer: {}]", inventoryEvent);
    try {
      inventoryService.reduceStock(inventoryEvent.getProducts());
      log.info(" [INVENTORY EVENT CONSUMER] - Successfully updated inventory for order");
      stringKafkaTemplate.send(inventoryStatusTopic, eventId, InventoryProcessStatus.COMPLETED.toString());
    } catch (Exception e) {
      log.error(" [INVENTORY EVENT CONSUMER] - Error updating inventory: {}", e.getMessage(), e);
      stringKafkaTemplate.send(inventoryStatusTopic, eventId, InventoryProcessStatus.FAILED.toString());
    }
  }


}
