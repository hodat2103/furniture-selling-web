package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductItemResponse;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductImage;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductItem;

public interface IProductImageService {
    ProductImage getProductImage(Long productItemId);
    String getImageUrl(String publicId);
}
