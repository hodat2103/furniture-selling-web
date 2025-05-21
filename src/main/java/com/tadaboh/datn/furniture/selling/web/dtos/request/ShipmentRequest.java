package com.tadaboh.datn.furniture.selling.web.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ShipmentRequest {
    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("employee_id")
    private Long employeeId;
}
