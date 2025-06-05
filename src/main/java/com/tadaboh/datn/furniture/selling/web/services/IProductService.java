package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.ProductRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductDetailResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ProductResponse;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface IProductService {
    ProductDetailResponse create(ProductRequest productRequest, List<MultipartFile> imageFiles) throws IOException;

    ProductDetailResponse update(Long id, ProductRequest productRequest, List<MultipartFile> imageFiles) throws IOException;

    ProductDetailResponse getById(Long id);

    ProductResponse getBySlug(String slug) throws Exception;

    ProductResponse getByCode(String code) throws Exception;

    Page<ProductResponse> getByCategoryId(Long categoryId, Pageable pageable) throws Exception;

    Page<ProductResponse> getByTag(String tag, Pageable pageable);


    Page<ProductResponse> filterProduct(String slug, Long categoryId, Long supplierId, BigDecimal minPrice, BigDecimal maxPrice, PageRequest pageRequest);

    List<String> getImageUrlFromPublicId (Long productItemId) throws Exception;

    void deleteSoften(Long id);
    void deleteStiffen(Long id);
}
