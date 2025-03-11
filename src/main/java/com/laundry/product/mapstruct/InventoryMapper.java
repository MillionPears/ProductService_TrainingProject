package com.laundry.product.mapstruct;


import com.laundry.product.dto.response.InventoryResponse;
import com.laundry.product.entity.Inventory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
  InventoryResponse toDTO(Inventory inventory);
}
