package com.laundry.product.mapstruct;


import com.laundry.product.dto.request.ProductCreateRequest;
import com.laundry.product.dto.request.ProductUpdateRequest;
import com.laundry.product.dto.response.ProductResponse;
import com.laundry.product.entity.Product;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ProductMapper {
  Product toEntity(ProductCreateRequest productCreateRequest);
  ProductResponse toDTO(Product product);
  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void setProductUpdateRequest(ProductUpdateRequest productUpdateRequest, @MappingTarget Product product);

}
