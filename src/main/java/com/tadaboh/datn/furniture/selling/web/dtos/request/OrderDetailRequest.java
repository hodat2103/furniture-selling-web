package com.tadaboh.datn.furniture.selling.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductItem;
import lombok.Data;

@Data
public class OrderDetailRequest {

    @JsonProperty("product_item_id")
    private Long productId;

    @JsonProperty("quantity")
    private int quantity;
}
