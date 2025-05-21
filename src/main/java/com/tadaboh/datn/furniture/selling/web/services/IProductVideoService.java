package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductItemResponse;

public interface IProductVideoService {
    ProductItemResponse getProductImage(Long productItemId);

}
