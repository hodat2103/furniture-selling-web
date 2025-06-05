package com.tadaboh.datn.furniture.selling.web.services;

import com.tadaboh.datn.furniture.selling.web.dtos.request.PaymentRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.PaymentResponse;

public interface IPaymentService {
    PaymentResponse savePayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentById(Long paymentId);

    PaymentResponse updatePaymentStatus(Long paymentId, String paymentStatus);
}
