package com.laundry.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "inventory")
public class Inventory extends AbstractVersionedEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private UUID id;

  @Column(name = "product_id", nullable = false)
  private UUID productId;

  @NotNull
  @Min(0)
  @Column(name = "available_quantity")
  private Integer availableQuantity;

  @NotNull
  @Column(name = "reserved_quantity")
  private Integer reservedQuantity;


}
