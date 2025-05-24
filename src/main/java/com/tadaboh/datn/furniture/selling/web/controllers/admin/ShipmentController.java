package com.tadaboh.datn.furniture.selling.web.controllers.admin;

import com.tadaboh.datn.furniture.selling.web.dtos.request.ShipmentRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ShipmentResponse;
import com.tadaboh.datn.furniture.selling.web.services.IShipmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}shipments")
@Tag(name = "Shipment", description = "Shipment API")
public class ShipmentController {
    private final IShipmentService shipmentService;
    @PostMapping
    public ResponseEntity<ResponseSuccess> create (@RequestBody ShipmentRequest shipmentRequest) {
        ShipmentResponse shipmentResponse = shipmentService.createShipmentForOrder(shipmentRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.CREATED, "Nhận kiện hàng thành công. Vui lòng hãy vận chuyển kiện hàng đến khách hàng !! ", shipmentResponse);
        return ResponseEntity.ok(responseSuccess);
    }

    @PutMapping("/{code}")
    public ResponseEntity<ResponseSuccess> updateStatus(@PathVariable String code, @RequestParam String shipmentStatus) {
        ShipmentResponse shipmentResponse = shipmentService.updateStatus(code, shipmentStatus);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "Cập nhật kiện hàng thành công", shipmentResponse);
        return ResponseEntity.ok(responseSuccess);
    }
}
