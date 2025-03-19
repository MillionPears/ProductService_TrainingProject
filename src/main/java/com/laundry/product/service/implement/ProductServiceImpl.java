package com.laundry.product.service.implement;

import com.laundry.product.dto.request.ProductCreateRequest;
import com.laundry.product.dto.response.ProductResponse;
import com.laundry.product.entity.Product;
import com.laundry.product.exception.CustomException;
import com.laundry.product.exception.ErrorCode;
import com.laundry.product.mapstruct.ProductMapper;
import com.laundry.product.repository.ProductRepository;
import com.laundry.product.service.InventoryService;
import com.laundry.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper mapper;
  private final InventoryService inventoryService;

  @Override
  @Transactional
  public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {
    Product product = mapper.toEntity(productCreateRequest);
    if(productRepository.existsByName(product.getName())) throw new CustomException(ErrorCode.PRODUCT_NAME_ALREADY_EXISTS,product.getName());
    product = productRepository.save(product);
    inventoryService.createInventory(product.getId());
    return mapper.toDTO(product);
  }

  @Override
  public ProductResponse getProductById(UUID id) {
    Product product = productRepository.findById(id).orElseThrow(
      ()-> new CustomException(ErrorCode.PRODUCT_NOT_FOUND,id)
    );
    return mapper.toDTO(product);
  }

  @Override
  public List<ProductResponse> getAllUser() {
    List<Product> list = productRepository.findAll();
    return list.stream()
      .map(mapper::toDTO).toList();
  }

  @Override
  public Page<ProductResponse> filterProductByNameAndPrice(String name, BigDecimal minPrice, BigDecimal maxPrice, String sortBy, String sortDirection, Pageable pageable) {
    Sort sort =Sort.by(Sort.Direction.fromString(sortDirection),sortBy);
    Pageable pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),sort);
//    Page<Product> productPage = productRepository.filterByNameAndPrice(name, minPrice, maxPrice, pageable);
//    return productPage.map(mapper::toDTO);
    return null;
  }


  @Override
  public List<ProductResponse> getProductsByIds(List<UUID> productIds) {
    List<Product> products = productRepository.findAllById(productIds);
    return products.stream()
      .map(mapper::toDTO).toList();
  }
}
