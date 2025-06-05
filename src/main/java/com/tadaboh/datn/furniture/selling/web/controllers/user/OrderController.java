package com.tadaboh.datn.furniture.selling.web.controllers.user;

import com.tadaboh.datn.furniture.selling.web.dtos.request.OrderRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.ResponseSuccess;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.OrderResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.OrderCreationException;
import com.tadaboh.datn.furniture.selling.web.services.IOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@Slf4j
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

    @GetMapping("")
    public ResponseEntity<ResponseSuccess> searchOrders(
            @RequestParam(name = "keyword", required = false) String keyword,
            @RequestParam(name = "fullName", required = false) String fullName,
            @RequestParam(name = "productName", required = false) String productName,
            @RequestParam(name = "orderCode", required = false) String orderCode,
            @RequestParam(name = "startDateTime", required = false) LocalDateTime startDateTime,
            @RequestParam(name = "endDateTime", required = false) LocalDateTime endDateTime,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<OrderResponse> orderResponses = orderService.searchOrders(
                keyword, fullName, productName, orderCode, startDateTime, endDateTime, PageRequest.of(page, size));
        ResponseSuccess responseSuccess = new ResponseSuccess(HttpStatus.OK, "Orders retrieved successfully", orderResponses);
        log.info("Orders retrieved: {}", orderResponses);
        return ResponseEntity.ok(responseSuccess);
}

}
