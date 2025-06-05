package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.configurations.VNPayConfig;
import com.tadaboh.datn.furniture.selling.web.dtos.request.PaymentRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.PaymentResponse;
import com.tadaboh.datn.furniture.selling.web.enums.PaymentStatusEnum;
import com.tadaboh.datn.furniture.selling.web.models.users.Order;
import com.tadaboh.datn.furniture.selling.web.models.users.Payment;
import com.tadaboh.datn.furniture.selling.web.repositories.OrderRepository;
import com.tadaboh.datn.furniture.selling.web.repositories.PaymentRepository;
import com.tadaboh.datn.furniture.selling.web.services.IPaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService implements IPaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    @Override
    public PaymentResponse savePayment(PaymentRequest paymentRequest) {
        Order order = orderRepository.findById(paymentRequest.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("Order not found"));
        Payment payment = Payment.builder()
                .order(order)
                .amount(paymentRequest.getAmount())
                .paymentMethod(paymentRequest.getPaymentMethod())
                .paymentStatus(PaymentStatusEnum.COMPLETED)
                .build();
        Payment savedPayment = paymentRepository.save(payment);
        return PaymentResponse.fromPayment(savedPayment);
    }

    @Override
    public PaymentResponse getPaymentById(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        return PaymentResponse.fromPayment(payment);
    }

    @Override
    public PaymentResponse updatePaymentStatus(Long paymentId, String paymentStatus) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found"));
        payment.setPaymentStatus(PaymentStatusEnum.valueOf(paymentStatus));
        Payment updatedPayment = paymentRepository.save(payment);
        return PaymentResponse.fromPayment(updatedPayment);
    }
}
