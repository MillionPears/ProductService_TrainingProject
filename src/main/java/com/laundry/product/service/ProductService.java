package com.laundry.product.service;


import com.laundry.product.dto.request.ProductCreateRequest;
import com.laundry.product.dto.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ProductService {
  ProductResponse createProduct(ProductCreateRequest productCreateRequest);
  ProductResponse getProductById(UUID id);
  List<ProductResponse> getAllUser();
  Page<ProductResponse> filterProductByNameAndPrice(String name,
                                                    BigDecimal minPrice,
                                                    BigDecimal maxPrice,
                                                    String sortBy,
                                                    String sortDirection,
                                                    Pageable pageable);


}
