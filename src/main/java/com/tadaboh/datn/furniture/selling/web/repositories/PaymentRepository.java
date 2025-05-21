package com.tadaboh.datn.furniture.selling.web.repositories;

import com.tadaboh.datn.furniture.selling.web.models.users.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
