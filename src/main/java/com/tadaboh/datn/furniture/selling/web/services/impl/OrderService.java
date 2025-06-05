package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.OrderDetailRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.request.OrderRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.OrderDetailResponse;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.OrderResponse;
import com.tadaboh.datn.furniture.selling.web.enums.OrderStatusEnum;
import com.tadaboh.datn.furniture.selling.web.enums.PaymentStatusEnum;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.exceptions.OrderCancelException;
import com.tadaboh.datn.furniture.selling.web.exceptions.OrderCreationException;
import com.tadaboh.datn.furniture.selling.web.helpers.GenerateCode;
import com.tadaboh.datn.furniture.selling.web.models.categories.PromotionCategory;
import com.tadaboh.datn.furniture.selling.web.models.categories.PromotionCondition;
import com.tadaboh.datn.furniture.selling.web.models.products.ProductItem;
import com.tadaboh.datn.furniture.selling.web.models.users.Order;
import com.tadaboh.datn.furniture.selling.web.models.users.OrderDetail;
import com.tadaboh.datn.furniture.selling.web.models.users.Payment;
import com.tadaboh.datn.furniture.selling.web.models.users.User;
import com.tadaboh.datn.furniture.selling.web.repositories.*;
import com.tadaboh.datn.furniture.selling.web.services.IOrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService implements IOrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final ProductItemRepository productItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final PromotionCategoryRepository promotionCategoryRepository;
    private final PromotionConditionRepository promotionConditionRepository;
    private final PaymentRepository paymentRepository;
    @Override
    public OrderResponse saveOrder(OrderRequest orderRequest) throws OrderCreationException {
        Optional<User> user = userRepository.findById(orderRequest.getUserId());

        if (orderRequest.getOrderDetails().isEmpty()) {
            throw new OrderCreationException("No items selected for order");
        }
        BigDecimal totalPrice = calculateTotalPrice(orderRequest.getOrderDetails());
        new Order();
        Order order = Order.builder()
                .user(user.get())
                .code(GenerateCode.generateOrderCode())
                .orderDate(LocalDateTime.now())
                .totalPrice(totalPrice)
                .finalPrice(BigDecimal.ZERO)
                .status(OrderStatusEnum.PENDING.name())
                .paymentMethod(orderRequest.getPaymentMethod())
                .build();
        Order saveOrder = orderRepository.save(order);

        List<OrderDetail> orderDetails = orderRequest.getOrderDetails().stream()
                .map(item -> OrderDetail.builder()
                        .order(saveOrder)
                        .productItem(productItemRepository.findByProductId(item.getProductId()))
                        .price(productItemRepository.findByProductId(item.getProductId()).getProduct().getPrice())
                        .quantity(item.getQuantity())
                        .build())
                .collect(Collectors.toList());
        List<OrderDetail> savedOrderDetails = orderDetailRepository.saveAll(orderDetails);
        for (OrderDetail orderDetail : savedOrderDetails) {
            ProductItem productItem = productItemRepository.findById(orderDetail.getProductItem().getId()).get();
            productItem.setStock(productItem.getStock() - orderDetail.getQuantity());
            productItemRepository.save(productItem);
        }
        saveOrder.setFinalPrice(calculateFinalPrice(orderRequest.getOrderDetails()));
        Order saveOrderFinalPrice = orderRepository.save(saveOrder);
        Payment payment = createPayment(saveOrder);
//        paymentRepository.save(payment);
        return OrderResponse.fromOrder(saveOrderFinalPrice, getOrderDetails(savedOrderDetails));
    }
    private BigDecimal calculateTotalPrice(List<OrderDetailRequest> orderDetails) {
        return orderDetails.stream()
                .map(item -> productItemRepository.findByProductId(item.getProductId()).getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    private BigDecimal calculateFinalPrice(List<OrderDetailRequest> orderDetails) {
        for (OrderDetailRequest orderDetail : orderDetails) {
            Optional<ProductItem> productItem = Optional.ofNullable(productItemRepository.findByProductId(orderDetail.getProductId()));
            Long categoryId = productItem.get().getProduct().getCategory().getId();
            List<PromotionCategory> promotionCategories = promotionCategoryRepository.findByCategoryId(categoryId);
            for (PromotionCategory promotionCategory : promotionCategories) {
                Optional<PromotionCondition> promotionCondition = promotionConditionRepository.findByPromotionId(promotionCategory.getPromotion().getId());
                if (promotionCondition.isPresent()) {
                    PromotionCondition condition = promotionCondition.get();
                    LocalDateTime now = LocalDateTime.now();
                    if (now.isAfter(condition.getStartDate()) && now.isBefore(condition.getEndDate())) {
                        BigDecimal discount = condition.getDiscountValue();
                        BigDecimal finalPrice = productItem.get()
                                .getProduct()
                                .getPrice()
                                .multiply(BigDecimal.ONE.subtract(discount))
                                .multiply(BigDecimal.valueOf(orderDetail.getQuantity()));
                        return finalPrice;
                    }
                }
            }
        }
        return calculateTotalPrice(orderDetails);
    }
    @Override
    public OrderResponse update(Long id, OrderRequest orderRequest) throws DataNotFoundException {
        return null;
    }

    @Override
    public List<OrderDetailResponse> getOrderDetails(List<OrderDetail> orderDetails) {
        return orderDetails.stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .collect(Collectors.toList());
    }

    @Override
    public Page<OrderResponse> searchOrders(String keyword,
                                            String fullName,
                                            String productName,
                                            String orderCode,
                                            LocalDateTime startDateTime,
                                            LocalDateTime endDateTime,
                                            Pageable pageable) {

        return orderRepository.searchOrders(keyword, fullName, productName, orderCode, startDateTime, endDateTime, pageable)
                .map(order -> {
                    List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.getId());
                    List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                            .map(OrderDetailResponse::fromOrderDetail)
                            .collect(Collectors.toList());
                    return OrderResponse.fromOrder(order, orderDetailResponses);
                });
    }

    @Override
    public OrderResponse getById(Long id) throws DataNotFoundException {
        Optional<Order> order = orderRepository.findById(id);
        if (order.isEmpty()) {
            throw new DataNotFoundException("Order not found");
        }
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(order.get().getId());
        if (orderDetails.isEmpty()) {
            throw new DataNotFoundException("Order Detail not found");
        }
        List<OrderDetailResponse> orderDetailResponses = orderDetails.stream()
                .map(OrderDetailResponse::fromOrderDetail)
                .collect(Collectors.toList());
        return OrderResponse.fromOrder(order.get(), orderDetailResponses);
    }

    @Override
    public void cancelOrderByOrderId(Long id) {

        Order order = orderRepository.findById(id).get();
        // chỉ cho hủy khi chưa giao hàng
        if (order.getStatus().equals(OrderStatusEnum.PENDING) && order.getUser().getId().equals(order.getUser().getId())) {
            order.setStatus(OrderStatusEnum.CANCELLED.name());
//            List items = order.get().stream().map(item -> item.getId()).collect(Collectors.toList());

            // refund amount
            /** Kiểm tra phương thức thanh toán
             * Nếu đơn đã thanh toón mà chưa  giao hàng thì hoàn tiền
             * Cập nhật trạng thái đơn hàng sang REFUNDED*/
        } else {
            throw new OrderCancelException("Cannot cancel this order ");
        }
        orderRepository.save(order);
    }

    @Override
    public void delete(Long id) throws DataNotFoundException {

    }

    @Override
    public Payment createPayment(Order order) {
        return Payment.builder()
                .paymentStatus(PaymentStatusEnum.PENDING)
                .order(order)
                .paymentDate(new Date())
                .paymentMethod(order.getPaymentMethod())
                .amount(order.getFinalPrice())
                .build();    }
}
