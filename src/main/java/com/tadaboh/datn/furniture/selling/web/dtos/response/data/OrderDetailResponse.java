package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.models.users.OrderDetail;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDetailResponse {
    private Long id;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("product_item_id")
    private Long productItem;

    private BigDecimal price;

    private int quantity;

    public static OrderDetailResponse fromOrderDetail(OrderDetail orderDetail) {

        return OrderDetailResponse.builder()
                .id(orderDetail.getId())
                .orderId(orderDetail.getOrder().getId())
                .productItem(orderDetail.getProductItem().getId())
                .price(orderDetail.getPrice())
                .quantity(orderDetail.getQuantity())
                .build();
    }

}
