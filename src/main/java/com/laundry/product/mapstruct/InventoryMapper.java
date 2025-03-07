package com.laundry.product.mapstruct;

import com.laundry.order.dto.response.InventoryResponse;
import com.laundry.order.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
  InventoryResponse toDTO(Inventory inventory);
}
