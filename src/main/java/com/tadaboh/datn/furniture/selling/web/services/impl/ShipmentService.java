package com.tadaboh.datn.furniture.selling.web.services.impl;

import com.tadaboh.datn.furniture.selling.web.dtos.request.ShipmentRequest;
import com.tadaboh.datn.furniture.selling.web.dtos.response.data.ShipmentResponse;
import com.tadaboh.datn.furniture.selling.web.enums.ShipmentStatusEnum;
import com.tadaboh.datn.furniture.selling.web.exceptions.DataNotFoundException;
import com.tadaboh.datn.furniture.selling.web.helpers.GenerateCode;
import com.tadaboh.datn.furniture.selling.web.models.users.Employee;
import com.tadaboh.datn.furniture.selling.web.models.users.Order;
import com.tadaboh.datn.furniture.selling.web.models.users.OrderDetail;
import com.tadaboh.datn.furniture.selling.web.models.users.Shipment;
import com.tadaboh.datn.furniture.selling.web.repositories.EmployeeRepository;
import com.tadaboh.datn.furniture.selling.web.repositories.OrderDetailRepository;
import com.tadaboh.datn.furniture.selling.web.repositories.OrderRepository;
import com.tadaboh.datn.furniture.selling.web.repositories.ShipmentRepository;
import com.tadaboh.datn.furniture.selling.web.services.IShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShipmentService implements IShipmentService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final EmployeeRepository employeeRepository;
    private final ShipmentRepository shipmentRepository;
    private final DistanceService distanceService;
    @Override
    public ShipmentResponse createShipmentForOrder(ShipmentRequest shipmentRequest) {
        if (shipmentRequest.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        Optional<Order> optionalOrder = orderRepository.findById(shipmentRequest.getOrderId());
        if (optionalOrder.isEmpty()) {
            throw new DataNotFoundException("Order not found for ID: " + shipmentRequest.getOrderId());
        }
        Order existingOrder = optionalOrder.get();
        Employee existingEmployee = employeeRepository.findById(shipmentRequest.getEmployeeId()).orElseThrow(() -> new DataNotFoundException("Employee not found"));
        List<OrderDetail> orderDetails = orderDetailRepository.findByOrderId(existingOrder.getId());
        if (orderDetails.isEmpty()) {
            throw new DataNotFoundException("Not found item in order");
        }
        double distanceUser = distanceService.calculateDistance(existingOrder.getUser().getAddress());
        Shipment shipment = new Shipment();
        shipment.setCode(GenerateCode.generateShipmentCode());
        shipment.setShipmentDate(new Date());
        shipment.setExpectedDeliveryDate(calculateExpectDeliveryDate(distanceUser));
        shipment.setStatus(ShipmentStatusEnum.PENDING.getStatus());
        shipment.setEmployee(existingEmployee);
        shipment.setOrder(existingOrder);
        shipment.setShippingFee(ShipmentService.calculateShipmentFee(distanceUser, orderDetails.size()));
        log.info("Shipping fee: {}", shipment.getShippingFee());
        shipment = shipmentRepository.save(shipment);

        existingOrder.setFinalPrice(existingOrder.getFinalPrice().add(shipment.getShippingFee()));
        log.info("Total price: {}", existingOrder.getFinalPrice());
        orderRepository.save(existingOrder);

        return ShipmentResponse.fromShipment(shipment);
    }
    public static Date calculateExpectDeliveryDate(double distance) {
        int daysToAdd;

        if (distance <=8) {
            daysToAdd = 1;
        } else if (distance <= 15) {
            daysToAdd = 2;
        }  else if (distance <= 25) {
            daysToAdd = 3;
        }  else if (distance <= 30) {
            daysToAdd = 4;
        } else if (distance <= 50) {
            daysToAdd = 6;
        } else {
            daysToAdd = 8;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date()); // Ngày shipmentDate (ngày hiện tại)
        calendar.add(Calendar.DAY_OF_MONTH, daysToAdd); // Cộng thêm số ngày

        return calendar.getTime();
    }
    private static BigDecimal calculateShipmentFee(double distance, int itemCount) {
        BigDecimal baseFee;

        if (distance < 3) {
            baseFee = BigDecimal.valueOf(200000L);
        } else if (distance <= 10) {
            baseFee = BigDecimal.valueOf(300000L);
        } else if (distance <= 20) {
            baseFee = BigDecimal.valueOf(500000L);
        } else if (distance <= 100) {
            baseFee = BigDecimal.valueOf(500000L).add(BigDecimal.valueOf((distance - 20) * 10000L));
        } else {
            baseFee = BigDecimal.valueOf(1000000L); // Trên 100km cố định 1 triệu
        }

        BigDecimal itemFee = BigDecimal.ZERO;
        // neu kien hang < 2 item thi free
        if (itemCount > 2 && itemCount <= 5) {
            itemFee = BigDecimal.valueOf((itemCount - 2) * 50000L);
        } else if (itemCount > 5) {
            itemFee = BigDecimal.valueOf((itemCount - 2) * 100000L);
        }

        return baseFee.add(itemFee);
    }

    @Override
    public ShipmentResponse updateStatus(String shipmentCode, String shipmentStatus) {
        Optional<Shipment> shipmentOptional = Optional.ofNullable(shipmentRepository.findByCode(shipmentCode));
        if (shipmentOptional.isPresent()) {
            Shipment shipment = shipmentOptional.get();
            shipment.setStatus(shipmentStatus);
            shipmentRepository.save(shipment);
            return ShipmentResponse.fromShipment(shipment);
        } else {
            throw new DataNotFoundException("Shipment not found for code: " + shipmentCode);
        }
    }

    @Override
    public void deleteShipment(String shipmentId) {

    }

    @Override
    public List<ShipmentResponse> getAllShipmentsByOrderId(Long orderId) {
        return List.of();
    }
}
