package com.laundry.product.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laundry.product.enums.KafkaTopic;
import com.laundry.product.parser.Parser;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryKafkaProducer {
  private final KafkaTemplate<String, String> stringKafkaTemplate;

  private final String inventoryProcessStatus = KafkaTopic.INVENTORY_REDUCE_STOCK_STATUS.getTopicName();
  private final short replicationFactor = 1;
  private final int partitionNumber = 3;
//
//  public void sendInventorySuccessEvent(String key,String inventoryEvent) {
//    stringKafkaTemplate.send(inventoryProcessStatus,key,inventoryEvent);
//  }
//  public void sendInventoryFailEvent(String key,String inventoryEvent) {
//    stringKafkaTemplate.send(inventoryProcessStatus,key,inventoryEvent);
//  }


  @Bean
  public NewTopic inventoryNewTopic() {
    return new NewTopic(inventoryProcessStatus, partitionNumber, replicationFactor);
  }


}
