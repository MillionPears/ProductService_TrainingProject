package com.laundry.product.repository;

import com.laundry.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {
  boolean existsByName(String name);

//  @Query(value = "SELECT * FROM products p" +
//    "WHERE (:name ='' OR :name IS NULL OR p.name LIKE %:name% OR p.name IS NULL)" +
//    "AND (:minPrice IS NULL OR p.price >= :minPrice)" +
//    "AND (:maxPrice IS NULL OR p.price <= :maxPrice)",nativeQuery = true)
//  Page<Product> filterByNameAndPrice(@Param("name")String name,
//                                  @Param("minPrice")BigDecimal minPrice,
//                                  @Param("maxPrice") BigDecimal maxPrice,
//                                  Pageable pageable);

}
