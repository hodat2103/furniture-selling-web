package com.tadaboh.datn.furniture.selling.web.dtos.response.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tadaboh.datn.furniture.selling.web.models.users.Shipment;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
public class ShipmentResponse {

    private Long id;

    @JsonProperty("code")
    private String code;

    @JsonProperty("order_id")
    private Long orderId;

    @JsonProperty("weight")
    private Float weight;

    @JsonProperty("shipment_fee")
    private BigDecimal shipmentFee;

    @JsonProperty("shipment_date")
    private Date shipmentDate;

    @JsonProperty("expect_delivery_date")
    private Date expectDeliveryDate;

    @JsonProperty("shipment_address")
    private String shipmentAddress;

    @JsonProperty("status")
    private String status;

    @JsonProperty("employee_id")
    private Long employeeId;

    public static ShipmentResponse fromShipment(Shipment shipment) {
        return ShipmentResponse.builder()
                .id(shipment.getId())
                .code(shipment.getCode())
                .orderId(shipment.getOrder().getId())
                .shipmentFee(shipment.getShippingFee())
                .shipmentDate(shipment.getShipmentDate())
                .expectDeliveryDate(shipment.getExpectedDeliveryDate())
                .shipmentAddress(shipment.getShipmentAddress())
                .status(shipment.getStatus())
                .employeeId(shipment.getEmployee().getId())
                .build();
    }
}
