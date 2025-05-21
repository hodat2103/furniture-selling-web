package com.tadaboh.datn.furniture.selling.web.enums;

public enum OrderStatusEnum {
    PENDING,       // Đơn hàng đang chờ xử lý
    PROCESSING,    // Đơn hàng đang được xử lý
    SHIPPED,       // Đơn hàng đã được giao đi
    DELIVERED,     // Đơn hàng đã được giao thành công
    CANCELLED,     // Đơn hàng đã bị hủy
    RETURNED;      // Đơn hàng đã được trả lại
}
