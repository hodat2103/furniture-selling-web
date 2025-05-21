package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.OrderRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.OrderDetailResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.OrderResponse;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.exceptions.OrderCreationException;
import com.tadaboh.datn.furniture.selling.web.models.users.Order;
import com.tadaboh.datn.furniture.selling.web.models.users.OrderDetail;
import com.tadaboh.datn.furniture.selling.web.models.users.Payment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IOrderService {
    OrderResponse saveOrder(OrderRequest orderRequest) throws OrderCreationException;
    OrderResponse update(Long id, OrderRequest orderRequest) throws DataNotFoundException;
    List<OrderDetailResponse> getOrderDetails(List<OrderDetail> orderDetails);
    Page<OrderResponse> searchOrders(String keyword,
                                     String fullName,
                                     String productName,
                                     String orderCode,
                                     LocalDateTime startDateTime,
                                     LocalDateTime endDateTime,
                                     Pageable pageable);

    OrderResponse getById(Long id) throws DataNotFoundException;
    void cancelOrderByOrderId(Long id);

    void delete(Long id) throws DataNotFoundException;
    Payment createPayment(Order order);
}
