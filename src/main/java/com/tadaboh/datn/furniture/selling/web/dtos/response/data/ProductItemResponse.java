package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.models.categories.Category;
import com.tadaboh.datn.furniture.selling.web.models.products.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductItemResponse {
    private Long id;
    private String description;

    private String size;

    @JsonProperty("material_ids")
    private List<Material> materials;

    @JsonProperty("product_image_id")
    private List<ProductImage> productImages;

    private String warranty;
    private Integer stock;

    public static ProductItemResponse fromProductItem(ProductItem productItem, List<ProductImage> productImages) {
        List<Material> materials = productItem.getMaterials();

        return ProductItemResponse.builder()

                .description(productItem.getDescription())
                .size(productItem.getSize())
                .materials(materials)
                .productImages(productImages)
                .warranty(productItem.getWarranty())
                .stock(productItem.getStock())
                .build();
    }

}
