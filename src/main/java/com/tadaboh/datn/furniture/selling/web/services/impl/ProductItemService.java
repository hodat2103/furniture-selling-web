package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductItemResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductImage;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductItem;
import com.tadaboh.datn.furniture.selling.web.repositories.ProductImageRepository;
import com.tadaboh.datn.furniture.selling.web.repositories.ProductItemRepository;
import com.tadaboh.datn.furniture.selling.web.services.IProductItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductItemService implements IProductItemService {
    private final ProductItemRepository productItemRepository;
    private final ProductImageRepository productImageRepository;
    private final CloudinaryService cloudinaryService;

    @Override
    public ProductItemResponse getProductItemById(Long id) {
        ProductItem productItem = productItemRepository.findById(id).orElseThrow(
                () -> new DataNotFoundException("Product item not found")
        );
        List<ProductImage> productImages = productImageRepository.findByProductItemId(productItem.getId());
        return ProductItemResponse.fromProductItem(productItem, productImages);
    }

    @Override
    public ProductItemResponse getProductItemByProductId(Long productId) {
        ProductItem productItem = productItemRepository.findByProductId(productId);
        if (productItem == null) {
            throw new DataNotFoundException("Product item not found");
        }
        List<ProductImage> productImages = productImageRepository.findByProductItemId(productItem.getId());
        return ProductItemResponse.fromProductItem(productItem, productImages);
    }

    @Override
    public int getStockByProductId(Long productId) {
        return productItemRepository.getStockByProductId(productId);
    }

}


