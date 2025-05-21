package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.products.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    @Query("SELECT pi FROM ProductImage pi WHERE pi.productItem.id = :productItemId")
    List<ProductImage> findByProductItemId(Long productItemId);  // Find product image by product item id

    @Query("SELECT pi FROM ProductImage pi WHERE pi.productItem.id = :productItemId AND pi.imageType = 'MAIN'")
    List<ProductImage> findByProductItemIdAndImageType(Long productItemId);  // Find product image by product item id and id

    @Query("SELECT pi FROM ProductImage pi WHERE pi.imageUrl = :publicId")
    String getImageUrl (String publicId);
}
