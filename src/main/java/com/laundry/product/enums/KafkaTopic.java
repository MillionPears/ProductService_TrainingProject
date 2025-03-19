package com.laundry.product.enums;

import lombok.Getter;

@Getter
public enum KafkaTopic {
  NOTIFY_INVENTORY_COMPENSATION_ACTION("inventory.compensation");

  private final String topicName;

  KafkaTopic(String topicName) {
    this.topicName = topicName;
  }
}
