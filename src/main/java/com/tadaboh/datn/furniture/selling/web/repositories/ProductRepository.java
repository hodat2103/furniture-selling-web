package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductResponse;
import com.tadaboh.datn.furniture.selling.web.models.products.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findBySlug(String slug);

    List<Product> findByCategoryId(Long categoryId);

    @Query("SELECT p FROM Product p WHERE " +
            "(:tag IS NULL OR :tag = '' OR p.tag = :tag)")
    Page<Product> findByTag(String tag, Pageable pageable);


    @Query("SELECT p FROM Product p WHERE " +
            "(:slug IS NULL OR :slug = '' OR p.slug LIKE %:slug%) AND " +
            "(:categoryId IS NULL OR p.category.id = :categoryId) AND " +
            "(:supplierId IS NULL OR p.supplier.id = :supplierId) AND " +
            "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    Page<Product> filterProduct(String slug, Long categoryId, Long supplierId, BigDecimal minPrice, BigDecimal maxPrice, PageRequest pageRequest);

    Product findByCode(String code);
}
