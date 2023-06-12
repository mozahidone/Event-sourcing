package com.example.eventsourcing.eventstore.service;

import com.example.eventsourcing.eventstore.domain.writemodel.Payment;
import com.example.eventsourcing.eventstore.eventsourcing.Command;
import com.example.eventsourcing.eventstore.eventsourcing.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentCommandHandler {

  private final PaymentEventStore eventStore;

  public void process(Command command) {
    Objects.requireNonNull(command);
    log.debug("Processing command {}", command);
    UUID paymentId = command.getAggregateId();
    List<Event> events = eventStore.readEvents(paymentId);
    Payment payment = new Payment(paymentId, events);
    payment.process(command);
    long expectedRevision = command.getExpectedRevision();
    for (Event event : payment.getChanges()) {
      expectedRevision = eventStore.append(event, expectedRevision);
    }
  }
}
