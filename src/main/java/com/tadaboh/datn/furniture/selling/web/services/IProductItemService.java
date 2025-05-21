package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductItemResponse;

public interface IProductItemService {
    ProductItemResponse getProductItemById(Long id);

    ProductItemResponse getProductItemByProductId(Long productId);

    int getStockByProductId(Long productId);
}
