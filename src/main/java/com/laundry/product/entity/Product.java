package com.laundry.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product extends AbstractVersionedEntity {

  @Id
  @Column(name = "id")
  private UUID id = UUID.randomUUID();

  @NotNull
  @Column(name = "name", unique = true)
  private String name;

  @NotNull
  @Column(name = "description")
  private String description;

  @NotNull
  @Column(name = "price")
  private BigDecimal price;

}
