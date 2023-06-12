package com.example.eventsourcing.eventstore.service;

import com.example.eventsourcing.eventstore.repository.PaymentRepository;
import com.example.eventsourcing.eventstore.domain.readmodel.Payment;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentReadModelUpdater {

  private final PaymentRepository repository;

  public void saveOrUpdate(Payment payment) {
    Objects.requireNonNull(payment);
    log.debug("Updating read model for payment {}", payment);
    repository.save(payment);
  }
}
