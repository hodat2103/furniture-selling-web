package com.tadaboh.datn.furniture.selling.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.enums.ProductTagEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {
    private String name;

    private ProductTagEnum tag;

    private String description;

    private String size;

    @JsonProperty("material_ids")
    private List<Long> materialIds;

    private BigDecimal price;

    private Integer stock;

    private String warranty;

    @JsonProperty("category_id")
    private Long categoryId;

    @JsonProperty("supplier_id")
    private Long supplierId;
}
