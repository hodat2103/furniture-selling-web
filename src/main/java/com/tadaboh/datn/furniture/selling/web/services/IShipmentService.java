package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.ShipmentRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ShipmentResponse;
import com.tadaboh.datn.furniture.selling.web.models.users.Shipment;

import java.util.List;

public interface IShipmentService {
    ShipmentResponse createShipmentForOrder(ShipmentRequest shipmentRequest);

    ShipmentResponse updateShipment(String shipmentCode, String shipmentMethod, String trackingNumber);

    void deleteShipment(String shipmentCode);

    List<ShipmentResponse> getAllShipmentsByOrderId(Long orderId);
}
