package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.products.ProductItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductItemRepository extends JpaRepository<ProductItem, Long> {

    @Query("SELECT SUM(pi.stock) FROM ProductItem pi WHERE pi.product.id = :productId")
    int getStockByProductId(Long productId);

    @Query("SELECT pi FROM ProductItem pi where pi.product.id = :productId")
    ProductItem findByProductId(Long productId);
}
