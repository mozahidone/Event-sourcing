package com.example.eventsourcing.eventstore.repository;

import com.example.eventsourcing.eventstore.domain.readmodel.Payment;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {}
