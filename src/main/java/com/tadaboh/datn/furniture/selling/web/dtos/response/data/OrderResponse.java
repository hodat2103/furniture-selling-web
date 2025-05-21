package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.enums.OrderStatusEnum;
import com.tadaboh.datn.furniture.selling.web.models.users.Order;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("order_date")
    private LocalDateTime orderDate;

    @JsonProperty("total_price")
    private BigDecimal totalPrice;

    @JsonProperty("final_price")
    private BigDecimal finalPrice;

    @JsonProperty("status")
    private OrderStatusEnum status;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @JsonProperty("orderItems")
    private List<OrderDetailResponse> orderDetailResponses;

    public static OrderResponse fromOrder(Order order, List<OrderDetailResponse> orderDetailResponses) {

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .orderDate(order.getOrderDate())
                .totalPrice(order.getTotalPrice())
                .finalPrice(order.getFinalPrice())
                .status(order.getStatus())
                .paymentMethod(order.getPaymentMethod())
                .orderDetailResponses(orderDetailResponses)
                .build();
    }
}
