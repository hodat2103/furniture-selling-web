package com.tadaboh.datn.furniture.selling.web.controllers.user;

import com.tadaboh.datn.furniture.selling.web.dtos.request.OrderRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.OrderResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.OrderCreationException;
import com.tadaboh.datn.furniture.selling.web.services.IOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}orders")
@Tag(name = "Order", description = "Order API")
public class OrderController {
    private final IOrderService orderService;
    @PostMapping()
    public ResponseEntity<ResponseSuccess> createOrder(@RequestBody OrderRequest orderRequest) throws OrderCreationException {
        OrderResponse orderResponse = orderService.saveOrder(orderRequest);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.CREATED, "Order created successfully", orderResponse);
        return ResponseEntity.ok(responseSuccess);
    }
    @PutMapping("/cancel/{id}")
    public ResponseEntity<ResponseSuccess> cancelOrder(@PathVariable Long id){
        orderService.cancelOrderByOrderId(id);
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.CREATED, "Your order has been canceled successfully", null);
        return ResponseEntity.ok(responseSuccess);
    }
}
