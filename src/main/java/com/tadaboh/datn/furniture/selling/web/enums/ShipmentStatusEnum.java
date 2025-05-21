package com.tadaboh.datn.furniture.selling.web.enums;

import lombok.Getter;

@Getter
public enum ShipmentStatusEnum {
    PENDING("pending"),      // Đơn hàng chưa bắt đầu vận chuyển
    SHIPPING("shipping"),    // Đang trong quá trình vận chuyển
    DELIVERED("delivered"),  // Đã giao hàng thành công
    CANCELLED("cancelled");  // Đơn hàng bị hủy

    private final String status;

    ShipmentStatusEnum(String status) {
        this.status = status;
    }

}
