package com.tadaboh.datn.furniture.selling.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class PaymentRequest {
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("payment_date")
    private Date paymentDate;

    @JsonProperty("amount")
    private BigDecimal amount;

    @JsonProperty("payment_method")
    private String paymentMethod;

}
