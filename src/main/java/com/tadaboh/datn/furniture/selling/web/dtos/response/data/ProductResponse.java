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
    private ProductTagEnum tag;
    private BigDecimal price;

    @JsonProperty("category_id")
    private Long category;

    @JsonProperty("supplier_id")
    private Supplier supplier;

    @JsonProperty("product_images")
    private List<ProductImage> productImages;

    @JsonProperty("is_active")
    private boolean isActive;

    public static ProductResponse fromProduct(Product product, List<ProductImage> productImages) {

        ProductResponse productResponse = ProductResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .slug(product.getSlug())
                .tag(product.getTag())
                .price(product.getPrice())
                .category(product.getCategory().getId())
                .supplier(product.getSupplier())
                .productImages(productImages)
                .isActive(product.getIsActive())
                .build();

        productResponse.setCreatedAt(product.getCreatedAt());
        productResponse.setUpdatedAt(product.getUpdatedAt());

        return productResponse;
    }

}