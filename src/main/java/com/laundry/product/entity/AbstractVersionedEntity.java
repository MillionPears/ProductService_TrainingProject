package com.laundry.product.entity;

import com.laundry.order.entity.Auditor;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public abstract class AbstractVersionedEntity extends Auditor {
  @Version
  private Long version;
}
