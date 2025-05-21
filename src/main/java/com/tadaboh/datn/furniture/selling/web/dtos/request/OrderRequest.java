package com.tadaboh.datn.furniture.selling.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.OrderDetailResponse;
import com.tadaboh.datn.furniture.selling.web.enums.OrderStatusEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductItem;
import com.tadaboh.datn.furniture.selling.web.models.users.Order;
import com.tadaboh.datn.furniture.selling.web.models.users.OrderDetail;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("status")
    private OrderStatusEnum status;

    @JsonProperty("payment_method")
    private String paymentMethod;

    @NotBlank(message = "Item not empty")
    private List<OrderDetailRequest> orderDetails;
}
