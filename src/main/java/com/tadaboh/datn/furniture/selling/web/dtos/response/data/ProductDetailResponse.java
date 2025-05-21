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
public class ProductDetailResponse extends AuditableResponse {
    private Long id;
    private String code;
    private String name;
    private String description;
    private String slug;
    private ProductTagEnum tag;
    private BigDecimal price;

    @JsonProperty("category_id")
    private Category category;

    @JsonProperty("supplier_id")
    private Supplier supplier;

    private String size;

    @JsonProperty("material_ids")
    private List<Material> materials;

    @JsonProperty("product_image_id")
    private List<ProductImage> productImages;

    private String warranty;
    private Integer stock;

    @JsonProperty("is_active")
    private Boolean isActive;

    public static ProductDetailResponse fromProductDetail(Product product, ProductItem productItem, List<ProductImage> productImages) {
        List<Material> materials = productItem.getMaterials();

        ProductDetailResponse productDetailResponse = ProductDetailResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .slug(product.getSlug())
                .tag(product.getTag())
                .description(productItem.getDescription())
                .price(product.getPrice())
                .category(product.getCategory())
                .supplier(product.getSupplier())
                .size(productItem.getSize())
                .materials(materials)
                .productImages(productImages)
                .warranty(productItem.getWarranty())
                .stock(productItem.getStock())
                .isActive(product.getIsActive())
                .build();

        productDetailResponse.setCreatedAt(product.getCreatedAt());
        productDetailResponse.setUpdatedAt(product.getUpdatedAt());

        return productDetailResponse;
    }
}

