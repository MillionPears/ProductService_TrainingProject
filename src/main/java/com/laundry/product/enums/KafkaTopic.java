package com.laundry.product.enums;

import lombok.Getter;

@Getter
public enum KafkaTopic {
  INVENTORY_REDUCE_STOCK_EVENT("inventory.update.stock"),
  INVENTORY_REDUCE_STOCK_STATUS("inventory.update.status");
  private final String topicName;

  KafkaTopic(String topicName) {
    this.topicName = topicName;
  }
}
