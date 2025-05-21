//package com.tadaboh.datn.furniture.selling.web.dtos.response.data;
//
//import com.fasterxml.jackson.annotation.JsonProperty;
//import com.tadaboh.datn.furniture.selling.web.dtos.response.AuditableResponse;
//import com.tadaboh.datn.furniture.selling.web.models.categories.Category;
//import com.tadaboh.datn.furniture.selling.web.models.products.Product;
//import com.tadaboh.datn.furniture.selling.web.models.products.ProductImage;
//import com.tadaboh.datn.furniture.selling.web.models.products.Supplier;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.math.BigDecimal;
//
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class ProductImageResponse extends AuditableResponse {
//    private Long id;
//    private String code;
//    private String name;
//    private String slug;
//    private String tag;
//    private BigDecimal price;
//
//    @JsonProperty("category_id")
//    private Category category;
//
//    @JsonProperty("supplier_id")
//    private Supplier supplier;
//
//    @JsonProperty("image_url")
//    private String imageUrl;
//
//    @JsonProperty("is_active")
//    private boolean isActive;
//
//    public static ProductImageResponse fromProduct(ProductImage productImage) {
//
//        ProductImageResponse productResponse = ProductImageResponse.builder()
//                .id(product.getId())
//                .code(product.getCode())
//                .name(product.getName())
//                .slug(product.getSlug())
//                .tag(product.getTag().getValue())
//                .price(product.getPrice())
//                .category(product.getCategory())
//                .supplier(product.getSupplier())
//                .imageUrl(productImage.getImageUrl())
//                .isActive(product.getIsActive())
//                .build();
//
//        productResponse.setCreatedAt(product.getCreatedAt());
//        productResponse.setUpdatedAt(product.getUpdatedAt());
//
//        return productResponse;
//    }
//
//}