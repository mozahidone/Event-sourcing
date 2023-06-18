package com.example.eventsourcing.eventstore.repository;

import com.example.eventsourcing.eventstore.domain.readmodel.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    List<Payment> findTop5ByOrderByPaymentDateDesc();
}
