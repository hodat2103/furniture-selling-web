package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.dtos.response.AuditableResponse;
import com.tadaboh.datn.furniture.selling.web.enums.ProductTagEnum;
import com.tadaboh.datn.furniture.selling.web.models.categories.Category;
import com.tadaboh.datn.furniture.selling.web.models.products.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse extends AuditableResponse {
    private Long id;
    private String code;
    private String name;
    private String slug;
    private String tag;
    private BigDecimal price;
    private int stock;
    @JsonProperty("category_id")
    private Long category;

    @JsonProperty("category_name")
    private String categoryName;

    @JsonProperty("supplier_id")
    private Supplier supplier;

    @JsonProperty("imageUrl")
    private String imageUrl;

    @JsonProperty("is_active")
    private boolean isActive;

    public static ProductResponse fromProduct(Product product, String imageUrl) {

        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .slug(product.getSlug())
                .tag(product.getTag())
                .price(product.getPrice())
                .category(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .supplier(product.getSupplier())
                .imageUrl(imageUrl)
                .isActive(product.getIsActive())
                .build();

        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());

        return productResponse;
    }
    public static ProductResponse fromProductAll(Product product, int stock, String imageUrl) {

        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .slug(product.getSlug())
                .tag(product.getTag())
                .price(product.getPrice())
                .stock(stock)
                .category(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .supplier(product.getSupplier())
                .imageUrl(imageUrl)
                .isActive(product.getIsActive())
                .build();

        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());

        return productResponse;
    }

}